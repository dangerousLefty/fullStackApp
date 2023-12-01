package com.hamzakhan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }

    @GetMapping("/greet")
    public greeting Greet(){
        greeting returnGreeting = new greeting(
                "Hello",
                List.of("Java", "Python", "anything ur mom uses"),
                new Person("ur mom's name", 69, 30_000)
        );
        return returnGreeting;
    }

    record Person(String name, int age, double availableCash){}

    record greeting (
            String greeting,
            List<String> favProgrammingLanguages,
            Person person
    ){}


    /*
    class greeting{
        private final String greet;

        public greeting(String greet) {
            this.greet = greet;
        }

        public String getGreet() {
            return greet;
        }

        @Override
        public String toString() {
            return "greeting{" +
                    "greet='" + greet + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            greeting greeting = (greeting) o;
            return Objects.equals(greet, greeting.greet);
        }

        @Override
        public int hashCode() {
            return Objects.hash(greet);
        }
    } */
}
