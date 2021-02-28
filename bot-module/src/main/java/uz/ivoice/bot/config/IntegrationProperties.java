package uz.ivoice.bot.config;

public class IntegrationProperties extends PropertyReader{
    public IntegrationProperties() {
        super("integration/integration.properties");
    }

    public String apiServiceUrl(){
        return getProperty("api.service.url");
    }
}
