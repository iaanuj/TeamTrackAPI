    package com.teamtrack;


    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.annotation.Bean;
    import org.springframework.data.mongodb.MongoDatabaseFactory;
    import org.springframework.data.mongodb.MongoTransactionManager;
    import org.springframework.retry.annotation.EnableRetry;
    import org.springframework.transaction.PlatformTransactionManager;
    import org.springframework.transaction.annotation.EnableTransactionManagement;

    @EnableTransactionManagement
    @SpringBootApplication
    @EnableRetry
    public class TeamTrack {

        public static void main(String[] args) {
            SpringApplication.run(TeamTrack.class, args);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(MongoDatabaseFactory dbFactory){
            return new MongoTransactionManager(dbFactory);
        }

    }