package org.example;

import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.staticfiles.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.example.BancoDeDados.AgendamentoRepositoryImpl;
import org.example.BancoDeDados.BarbeiroRepositoryImpl;
import org.example.BancoDeDados.ClienteRepositoryImpl;
import org.example.Controllers.AgendamentoController;
import org.example.Controllers.BarbeiroController;
import org.example.Controllers.ClienteController;
import org.example.Controllers.LoginController;
import org.example.Repositorys.AgendamentoRepository;
import org.example.Repositorys.BarbeiroRepository;
import org.example.Repositorys.ClienteRepository;
import org.example.Services.AgendamentoService;
import org.example.Services.BarbeiroService;
import org.example.Services.ClienteService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

public class AppBarbearia {
    private static final Logger logger = LogManager.getLogger(AppBarbearia.class);
    private static final int PORTA_PADRAO = 7000;
    private static final String PROP_PORTA_SERVIDOR = "porta.servidor";
    private final Properties propriedades;

    private Connection connection;

    public AppBarbearia() {
        this.propriedades = new Properties();
    }

    private void inicializarBanco() {
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        if (dbUrl == null)
            dbUrl = "jdbc:mysql://localhost:3307/sistema_barbearia?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        if (dbUser == null)
            dbUser = "root";
        if (dbPassword == null)
            dbPassword = "12345678";

        // Load MySQL driver explicitly before Flyway
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("MySQL Driver não encontrado!", e);
        }

        // Setup Flyway
        try {
            org.flywaydb.core.Flyway flyway = org.flywaydb.core.Flyway.configure()
                    .dataSource(dbUrl, dbUser, dbPassword)
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true)
                    .load();
            flyway.migrate();
            logger.info("Migrações do Flyway executadas com sucesso!");
        } catch (Exception e) {
            logger.error("Erro ao executar migrações do Flyway: " + e.getMessage(), e);
            // Continue startup - DB might already be migrated or we want app to try anyway
        }

        try {
            this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            logger.info("Conectado ao MySQL com sucesso!");
        } catch (SQLException e) {
            logger.error("Erro ao conectar no MySQL", e);
            System.exit(1);
        }
    }

    private void registrarServicos(JavalinConfig config) {
        ClienteRepository clienteRepository = new ClienteRepositoryImpl();
        ClienteService clienteService = new ClienteService(clienteRepository);
        BarbeiroRepository barbeiroRepository = new BarbeiroRepositoryImpl();
        AgendamentoRepository agendamentoRepository = new AgendamentoRepositoryImpl();
        BarbeiroService barbeiroService = new BarbeiroService(barbeiroRepository, agendamentoRepository,
                clienteRepository);

        AgendamentoService agendamentoService = new AgendamentoService(agendamentoRepository);
        config.appData(Keys.CLIENTE_SERVICE.key(), clienteService);
        config.appData(Keys.BARBEIRO_SERVICE.key(), barbeiroService);
        config.appData(Keys.AGENDAMENTO_SERVICE.key(), agendamentoService);
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

    public void configurarRotas(Javalin app) {
        LoginController loginController = new LoginController();
        app.get("/", ctx -> {
            ctx.redirect("/login");
        });

        app.get("/login", loginController::mostrarPaginaLogin);
        app.post("/login", loginController::processarLogin);
        app.get("/telaCliente", loginController::mostrarPaginaCliente);
        app.get("/perfil/{clienteId}", loginController::mostrarPerfil);
        app.get("/logOut", loginController::logOut);

        ClienteController clienteController = new ClienteController();
        app.get("/cadastro", clienteController::mostrarPaginaCadastro);
        app.post("/cadastro", clienteController::cadastrarCliente);
        app.get("/listarClientes", clienteController::listarClientes);
        app.post("/telaEditar", clienteController::editarCliente);

        BarbeiroController barbeiroController = new BarbeiroController();
        app.get("/barbeiro", barbeiroController::listarAgendamentos);

        AgendamentoController agendamentoController = new AgendamentoController();
        app.get("/novoAgendamento/{clienteId}", agendamentoController::mostrarPaginaAgendamento);
        app.post("/novoAgendamento", agendamentoController::criarAgendamento);
        app.get("/agendamentos/ocupados", ctx -> {
            String dataStr = ctx.queryParam("data");
            if (dataStr == null) {
                ctx.status(400);
                return;
            }
            LocalDate data = LocalDate.parse(dataStr);

            AgendamentoRepositoryImpl agendamentoRepository = new AgendamentoRepositoryImpl();

            List<LocalTime> horariosOcupados = agendamentoRepository.buscarHorariosOcupadosPorData(data);

            // Format timestamps to HH:mm string to match frontend expected format
            List<String> horariosFormatados = horariosOcupados.stream()
                    .map(time -> String.format("%02d:%02d", time.getHour(), time.getMinute()))
                    .toList();

            ctx.json(horariosFormatados);
        });
        app.get("/meusAgendamentos/{clienteId}", agendamentoController::listarAgendamentos);
        app.get("/historico/{clienteId}", agendamentoController::listarHistoricoDeAgendamento);
        app.delete("/agendamentos/{id}", agendamentoController::removerAgendamento);

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

        config.fileRenderer(new JavalinThymeleaf(templateEngine));
    }

    private Javalin inicializarJavalin() {
        Consumer<JavalinConfig> configConsumer = this::configureJavalin;
        return Javalin.create(configConsumer);
    }

    public void iniciar() {
        logger.info("Método iniciar() foi chamado!");
        Javalin app = inicializarJavalin();
        configurarPaginasDeErro(app);
        configurarRotas(app);

        inicializarBanco();

        app.exception(Exception.class, (e, ctx) -> {
            logger.error("Erro não tratado", e);
            ctx.status(500);
        });
        int porta = obterPortaServidor();
        logger.info("Iniciando servidor na porta: " + porta);
        app.start("0.0.0.0", porta);

    }

    public static void main(String[] args) {
        new AppBarbearia().iniciar();
    }

    public void configurarPaginasDeErro(Javalin app) {
        app.error(404, ctx -> ctx.render("erro_404.html"));
        app.error(500, ctx -> ctx.render("erro_500.html"));
    }

    private int obterPortaServidor() {
        String portEnv = System.getenv("PORT");
        if (portEnv != null) {
            try {
                return Integer.parseInt(portEnv);
            } catch (NumberFormatException e) {
                logger.error("Porta inválida em PORT, usando padrão: " + PORTA_PADRAO);
            }
        }
        return PORTA_PADRAO;
    }
}
