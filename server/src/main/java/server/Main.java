package server;

import org.apache.log4j.BasicConfigurator;

public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        NettyServer server = new NettyServer();
    }
}
