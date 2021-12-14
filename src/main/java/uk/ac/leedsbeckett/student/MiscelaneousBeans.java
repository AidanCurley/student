package uk.ac.leedsbeckett.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import uk.ac.leedsbeckett.student.model.*;
import uk.ac.leedsbeckett.student.model.CourseRepository;
import uk.ac.leedsbeckett.student.model.EnrolmentRepository;
import uk.ac.leedsbeckett.student.model.StudentRepository;

@Configuration
class MiscelaneousBeans {

    private static final Logger log = LoggerFactory.getLogger(MiscelaneousBeans.class);

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    CommandLineRunner initDatabase(StudentRepository studentRepository, CourseRepository courseRepository, EnrolmentRepository enrolmentRepository) {
        return args -> {
            Student walter = new Student("Walter", "White");
            Student jesse = new Student("Jesse", "Pinkman");
            studentRepository.save(walter);
            studentRepository.save(jesse);
            studentRepository.findAll().forEach(student -> log.info("Preloaded " + student));

            Course programming = new Course("Introduction to Programming", "An introductory course covering the fundamentals of object-oriented programming.", 150.00);
            Course ocpjp = new Course("OCPJP Exam Preparation", "A course covering the learning objectives for the latest OCPJP certification.", 350.00);
            Course springBoot = new Course("Spring Boot", "An intermediate course on how to make the most of Spring Boot's latest functionality.", 200.00);
            Course chemistry = new Course("Advanced Chemistry", "An advanced course covering the in-lab production of medical-grade synthetic substances.", 1150.00);
            courseRepository.save(programming);
            courseRepository.save(ocpjp);
            courseRepository.save(springBoot);
            courseRepository.save(chemistry);
            courseRepository.findAll().forEach(course -> log.info("Preloaded " + course));

            enrolmentRepository.save(new Enrolment(jesse, programming));
            enrolmentRepository.save(new Enrolment(walter, springBoot));
            enrolmentRepository.save(new Enrolment(walter, chemistry));
            courseRepository.findAll().forEach(enrolment -> log.info("Preloaded " + enrolment));
        };
    }
}