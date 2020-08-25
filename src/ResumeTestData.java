import ru.snx.webapp.model.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        EnumMap<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        resume.setContacts(contacts);

        EnumMap<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
        TextSection objective = new TextSection("Ведущий стажировок " +
                "и корпоративного обучения по " +
                "Java Web и Enterprise технологиям");
        sections.put(SectionType.OBJECTIVE, objective);

        TextSection personal = new TextSection("Аналитический склад ума, " +
                "сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры.");
        sections.put(SectionType.PERSONAL, personal);

        List<String> achieve = new ArrayList<>();
        achieve.add(achieve.size(), "Реализация протоколов по приему платежей всех основных платежных " +
                "системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        achieve.add(achieve.size(), "Создание JavaEE фреймворка для отказоустойчивого взаимодействия " +
                "слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). " +
                "Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. " +
                "Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achieve.add(achieve.size(), "Реализация c нуля Rich Internet Application приложения на стеке технологий " +
                "JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achieve.add(achieve.size(), "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением " +
                "на стеке: Scala/Play/Anorm/JQuery. " +
                "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achieve.add(achieve.size(), "Реализация двухфакторной аутентификации для онлайн платформы " +
                "управления проектами Wrike. Интеграция с Twilio, " +
                "DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achieve.add(achieve.size(), "С 2013 года: разработка проектов \"Разработка Web приложения\"," +
                "\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). " +
                "Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". " +
                "Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        BulletedListSection achievements = new BulletedListSection(achieve);
        sections.put(SectionType.ACHIEVEMENT, achievements);

        List<String> qual = new ArrayList<>();
        qual.add(qual.size(), "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qual.add(qual.size(), "Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qual.add(qual.size(), "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        qual.add(qual.size(), "MySQL, SQLite, MS SQL, HSQLDB");
        qual.add(qual.size(), "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qual.add(qual.size(), "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qual.add(qual.size(), "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), " +
                "Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, " +
                "Eclipse SWT, JUnit, Selenium (htmlelements)");
        qual.add(qual.size(), "Python: Django");
        qual.add(qual.size(), "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qual.add(qual.size(), "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qual.add(qual.size(), "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, " +
                "JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, " +
                "Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT");
        qual.add(qual.size(), "Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qual.add(qual.size(), "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, " +
                "JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qual.add(qual.size(), "Отличное знание и опыт применения концепций ООП, SOA, " +
                "шаблонов проектрирования, архитектурных шаблонов, " +
                "UML, функционального программирования");
        qual.add(qual.size(), "Родной русский, английский \"upper intermediate\"");
        BulletedListSection qualifications = new BulletedListSection(qual);
        sections.put(SectionType.QUALIFICATION, qualifications);

        List<Experience> exp = new ArrayList<>();
        exp.add(exp.size(), new Experience(
                "Alcatel",
                "http://www.alcatel.ru/",
                new WorkInterval(
                        YearMonth.of(1997, 9),
                        YearMonth.of(2005, 1),
                        "Инженер по аппаратному и программному тестированию",
                        "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).")


        ));
        exp.add(exp.size(), new Experience(
                "Siemens AG",
                "https://www.siemens.com/ru/ru/home.html",
                new WorkInterval(
                        YearMonth.of(2005, 1),
                        YearMonth.of(2007, 2),
                        "Разработчик ПО",
                        "Разработка информационной модели, проектирование интерфейсов, " +
                                "реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).")
        ));
        exp.add(exp.size(), new Experience(
                "Enkata",
                "http://enkata.com/",
                new WorkInterval(
                        YearMonth.of(2007, 3),
                        YearMonth.of(2008, 6),
                        "Разработчик ПО",
                        "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) " +
                                "частей кластерного J2EE приложения (OLAP, Data mining).")
        ));
        exp.add(exp.size(), new Experience(
                "Yota",
                "https://www.yota.ru/",
                new WorkInterval(
                        YearMonth.of(2008, 6),
                        YearMonth.of(2010, 12),
                        "Ведущий специалист",
                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                                "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). " +
                                "Реализация администрирования, статистики и мониторинга фреймворка. " +
                                "Разработка online JMX клиента (Python/ Jython, Django, ExtJS)")
        ));
        exp.add(exp.size(), new Experience(
                "Luxoft (Deutsche Bank)",
                "http://www.luxoft.ru/",
                new WorkInterval(
                        YearMonth.of(2010, 12),
                        YearMonth.of(2012, 4),
                        "Ведущий программист",
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, " +
                                "SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. " +
                                "Реализация RIA-приложения для администрирования, мониторинга и анализа результатов " +
                                "в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, " +
                                "ExtGWT (GXT), Highstock, Commet, HTML5.")
        ));
        exp.add(exp.size(), new Experience(
                "RIT Center",
                "",
                new WorkInterval(
                        YearMonth.of(2012, 4),
                        YearMonth.of(2014, 10),
                        "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                                "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                                "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД " +
                                "и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, " +
                                "1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                                "Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                                "Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, " +
                                "Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, " +
                                "Unix shell remote scripting via ssh tunnels, PL/Python")
        ));
        exp.add(exp.size(), new Experience(
                "Wrike",
                "https://www.wrike.com/",
                new WorkInterval(
                        YearMonth.of(2014, 10),
                        YearMonth.of(2016, 1),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")
        ));
        exp.add(exp.size(), new Experience(
                "Java Online Projects",
                "http://javaops.ru/",
                new WorkInterval(
                        YearMonth.of(2013, 10),
                        YearMonth.now(),
                        "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок.")
        ));
        OrganizationSection experience = new OrganizationSection(exp);
        sections.put(SectionType.EXPERIENCE, experience);

        List<Experience> edu = new ArrayList<>();
        edu.add(edu.size(), new Experience(
                "Coursera",
                "https://www.coursera.org/course/progfun",
                new WorkInterval(
                        YearMonth.of(2013, 3),
                        YearMonth.of(2013, 5),
                        "\"Functional Programming Principles in Scala\" by Martin Odersky")
        ));
        edu.add(edu.size(), new Experience(
                "Luxoft",
                "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                new WorkInterval(
                        YearMonth.of(2011, 3),
                        YearMonth.of(2011, 4),
                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"")
        ));
        edu.add(edu.size(), new Experience(
                "Siemens AG",
                "http://www.siemens.ru/",
                new WorkInterval(
                        YearMonth.of(2005, 1),
                        YearMonth.of(2005, 4),
                        "3 месяца обучения мобильным IN сетям (Берлин)")
        ));
        edu.add(edu.size(), new Experience(
                "Alcatel",
                "http://www.alcatel.ru/",
                new WorkInterval(
                        YearMonth.of(1997, 9),
                        YearMonth.of(1998, 3),
                        "6 месяцев обучения цифровым телефонным сетям (Москва)")
        ));
        edu.add(edu.size(), new Experience(
                "Санкт-Петербургский национальный исследовательский университет " +
                        "информационных технологий, механики и оптики",
                "http://www.ifmo.ru/",
                new WorkInterval(
                        YearMonth.of(1993, 9),
                        YearMonth.of(1996, 7),
                        "Аспирантура (программист С, С++)"),
                new WorkInterval(
                        YearMonth.of(1987, 9),
                        YearMonth.of(1993, 7),
                        "Инженер (программист Fortran, C)")
        ));
        edu.add(edu.size(), new Experience(
                "Заочная физико-техническая школа при МФТИ",
                "http://www.school.mipt.ru/",
                new WorkInterval(
                        YearMonth.of(1984, 9),
                        YearMonth.of(1987, 6),
                        "Закончил с отличием")
        ));
        OrganizationSection educations = new OrganizationSection(edu);
        sections.put(SectionType.EDUCATION, educations);

        resume.setSections(sections);

        System.out.println("ID: " + resume.getUuid());
        System.out.println("Full Name: " + resume.getFullName());
        System.out.println("Phone: " + resume.getContact(ContactType.PHONE));
        System.out.println("Skype: " + resume.getContact(ContactType.SKYPE));
        System.out.println("E-mail: " + resume.getContact(ContactType.EMAIL));
        System.out.println("LinkedIn: " + resume.getContact(ContactType.LINKEDIN));
        System.out.println("GitHub: " + resume.getContact(ContactType.GITHUB));
        System.out.println("StackOverflow: " + resume.getContact(ContactType.STACKOVERFLOW));
        System.out.println("Позиция: " + ((TextSection) resume.getSection(SectionType.OBJECTIVE)).getInformation());
        System.out.println("Личные качества: " + ((TextSection) resume.getSection(SectionType.PERSONAL)).getInformation());
        System.out.println("Достижения");
        List<String> ach = ((BulletedListSection) resume.getSection(SectionType.ACHIEVEMENT)).getInformation();
        for (String str : ach) {
            System.out.println(str);
        }
        System.out.println("Квалификация");
        List<String> qualify = ((BulletedListSection) resume.getSection(SectionType.QUALIFICATION)).getInformation();
        for (String str : qualify) {
            System.out.println(str);
        }
        System.out.println("Опыт работы");
        List<Experience> exper = ((OrganizationSection) resume.getSection(SectionType.EXPERIENCE)).getInformation();
        for (Experience obj : exper) {
            System.out.println(obj.getOrgName());
            System.out.println(obj.getUrl() == null ? obj.getUrl() : "Not presented !!!");
            for (WorkInterval w : obj.getWorks()) {
                System.out.println(w.getStart());
                System.out.println(w.getEnd());
                System.out.println(w.getPosition());
                System.out.println(w.getDescription());
            }

        }
        System.out.println("Образование");
        List<Experience> educat = ((OrganizationSection) resume.getSection(SectionType.EDUCATION)).getInformation();
        for (Experience obj : educat) {
            System.out.println(obj.getOrgName());
            System.out.println(obj.getUrl() == null ? obj.getUrl() : "Not presented !!");
            for (WorkInterval w : obj.getWorks()) {
                System.out.println(w.getStart());
                System.out.println(w.getEnd());
                System.out.println(w.getDescription());
            }
        }
    }
}
