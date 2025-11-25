package org.example;

import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.staticfiles.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.example.BancoDeDados.BarbeiroRepositoryImpl;
import org.example.BancoDeDados.ClienteRepositoryImpl;
import org.example.Controllers.BarbeiroController;
import org.example.Controllers.ClienteController;
import org.example.Controllers.LoginController;
import org.example.Repositorys.BarbeiroRepository;
import org.example.Repositorys.ClienteRepository;
import org.example.Services.BarbeiroService;
import org.example.Services.ClienteService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Consumer;

public class AppBarbearia {
    private static final Logger logger = LogManager.getLogger(AppBarbearia.class);
    private static final int PORTA_PADRAO = 7000;
    private static final String PROP_PORTA_SERVIDOR = "porta.servidor";
    private final Properties propriedades;

    public AppBarbearia() {
        this.propriedades = carregarPropriedades();
    }
    public Properties carregarPropriedades(){
        Properties prop = new Properties();
        try (InputStream input = AppBarbearia.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                logger.error("Arquivo application.properties não encontrado em /resources");
                System.exit(1);
            }
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar application.properties", e);
        }
        return prop;
    }
    private void registrarServicos(JavalinConfig config){
        ClienteRepository clienteRepository = new ClienteRepositoryImpl();
        ClienteService clienteService = new ClienteService(clienteRepository);
        BarbeiroRepository barbeiroRepository = new BarbeiroRepositoryImpl();
        BarbeiroService barbeiroService = new BarbeiroService(barbeiroRepository);
        config.appData(Keys.CLIENTE_SERVICE.key(), clienteService);
        config.appData(Keys.BARBEIRO_SERVICE.key(), barbeiroService);
    }
    private TemplateEngine configurarThymeleaf() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);
        return engine;
    }

    public void configurarRotas(Javalin app){
        LoginController loginController = new LoginController();
        app.get("/", ctx -> {
            logger.info("Entrou na rota /");
            ctx.redirect("/login");
        });

        app.get("/login", loginController::mostrarPaginaLogin);
        app.post("/login", loginController::processarLogin);

        ClienteController clienteController = new ClienteController();
        app.get("/cadastro", clienteController :: cadastrarCliente);
        app.get("/listarClientes", clienteController :: listarClientes);

        BarbeiroController barbeiroController = new BarbeiroController();
        app.get("listarClientes", barbeiroController :: listarCLientes);
        app.get("listarAgendamentos", barbeiroController :: listarAgendamentos);
    }
    private void configureJavalin(JavalinConfig config) {
        TemplateEngine templateEngine = configurarThymeleaf();

        config.events(event -> {
            event.serverStarting(() -> logger.info("Servidor Javalin está iniciando..."));
            event.serverStopping(() -> logger.info("Servidor parando..."));
            registrarServicos(config);

        });

        config.staticFiles.add(staticFileConfig -> {
            staticFileConfig.directory = "public";
            staticFileConfig.location = Location.CLASSPATH;
        });

        config.fileRenderer(new JavalinThymeleaf(templateEngine));    }

    private Javalin inicializarJavalin() {
        Consumer<JavalinConfig> configConsumer = this::configureJavalin;
        return Javalin.create(configConsumer);
    }
    public void iniciar() {
        logger.info("Método iniciar() foi chamado!");
        Javalin app = inicializarJavalin();
        configurarPaginasDeErro(app);
        configurarRotas(app);

        app.exception(Exception.class, (e, ctx) -> {
            logger.error("Erro não tratado", e);
            ctx.status(500);
        });
        app.start(obterPortaServidor());

    }
    public static void main(String[] args) {
        new AppBarbearia().iniciar();
    }
    public void configurarPaginasDeErro(Javalin app){
        app.error(404, ctx -> ctx.render("erro_404.html"));
        app.error(500, ctx -> ctx.render("erro_500.html"));
    }
    private int obterPortaServidor() {
        if (propriedades.containsKey(PROP_PORTA_SERVIDOR)) {
            try {
                return Integer.parseInt(propriedades.getProperty(PROP_PORTA_SERVIDOR));
            } catch (NumberFormatException e) {
                logger.error("Porta inválida, usando padrão: " + PORTA_PADRAO);
            }
        }
        return PORTA_PADRAO;
    }
}
