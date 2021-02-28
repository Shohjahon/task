package uz.ivoice.bot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import uz.ivoice.bot.command.AskCommand;

@Component
public class BotRunner implements CommandLineRunner, ExitCodeGenerator {
    private final AskCommand askCommand;
    private final CommandLine.IFactory factory;
    private int exitCode;

    public BotRunner(AskCommand askCommand,
                     CommandLine.IFactory factory) {
        this.askCommand = askCommand;
        this.factory = factory;
    }

    @Override
    public void run(String... args) throws Exception {
        exitCode = new CommandLine(askCommand, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
