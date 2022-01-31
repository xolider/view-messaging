package ovh.vicart.messaging.utils;

public class BackendConfiguration {

    private String host = "localhost";
    private int port = 8081;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    private BackendConfiguration() {

    }

    public static class Builder {
        private BackendConfiguration bc = new BackendConfiguration();

        public Builder host(final String host) {
            bc.host = host;
            return this;
        }

        public Builder port(final int port) {
            bc.port = port;
            return this;
        }

        public BackendConfiguration build() {
            return bc;
        }
    }
}
