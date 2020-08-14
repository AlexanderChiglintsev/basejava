package ru.snx.webapp;

import ru.snx.webapp.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        HashMap<ContactType, Contact> contacts = new HashMap<>();
        contacts.put(ContactType.PHONE, new Contact("Телефон", "+7(921) 855-0482"));
        contacts.put(ContactType.EMAIL, new Contact("E-mail", "gkislin@yandex.ru"));
        contacts.put(ContactType.SKYPE, new Contact("Skype", "grigory.kislin"));
        contacts.put(ContactType.LINKEDIN, new Contact("LinkedIn", "https://www.linkedin.com/in/gkislin"));
        contacts.put(ContactType.STACKOVERFLOW, new Contact("StackOverflow", "https://stackoverflow.com/users/548473"));
        contacts.put(ContactType.GITHUB, new Contact("GitHub", "https://github.com/gkislin"));
        resume.setContactList(contacts);

        HashMap<SectionType, AbstractSection> sections = new HashMap<>();
        AbstractSection<String> objective = new AbstractSection<>("Ведущий стажировок " +
                "и корпоративного обучения по " +
                "Java Web и Enterprise технологиям");
        sections.put(SectionType.OBJECTIVE, objective);

        AbstractSection<String> personal = new AbstractSection<>("Аналитический склад ума, " +
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
        AbstractSection<List<String>> achievements = new AbstractSection<>(achieve);
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
        AbstractSection<List<String>> qualifications = new AbstractSection<>(qual);
        sections.put(SectionType.QUALIFICATION, qualifications);

        List<SectionContent> exp = new ArrayList<>();
        exp.add(exp.size(), new SectionContent(
                "09/1997 - 01/2005",
                "Alcatel",
                "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."
        ));
        exp.add(exp.size(), new SectionContent(
                "01/2005 - 02/2007",
                "Siemens AG",
                "Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, " +
                        "реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."
        ));
        exp.add(exp.size(), new SectionContent(
                "03/2007 - 06/2008",
                "Enkata",
                "Разработчик ПО",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) " +
                        "частей кластерного J2EE приложения (OLAP, Data mining)."
        ));
        exp.add(exp.size(), new SectionContent(
                "06/2008 - 12/2010",
                "Yota",
                "Ведущий специалист",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                        "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). " +
                        "Реализация администрирования, статистики и мониторинга фреймворка. " +
                        "Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"
        ));
        exp.add(exp.size(), new SectionContent(
                "12/2010 - 04/2012",
                "Luxoft (Deutsche Bank)",
                "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, " +
                        "SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. " +
                        "Реализация RIA-приложения для администрирования, мониторинга и анализа результатов " +
                        "в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, " +
                        "ExtGWT (GXT), Highstock, Commet, HTML5."
        ));
        exp.add(exp.size(), new SectionContent(
                "04/2012 - 10/2014",
                "RIT Center",
                "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                        "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                        "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД " +
                        "и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, " +
                        "1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                        "Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                        "Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, " +
                        "Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, " +
                        "Unix shell remote scripting via ssh tunnels, PL/Python"
        ));
        exp.add(exp.size(), new SectionContent(
                "10/2014 - 01/2016",
                "Wrike",
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                        "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."
        ));
        exp.add(exp.size(), new SectionContent(
                "10/2013 - Сейчас",
                "Java Online Projects",
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок."
        ));
        AbstractSection<List<SectionContent>> experience = new AbstractSection<>(exp);
        sections.put(SectionType.EXPERIENCE, experience);

        List<SectionContent> edu = new ArrayList<>();
        edu.add(edu.size(), new SectionContent(
                "03/2013 - 05/2013",
                "Coursera",
                "",
                "\"Functional Programming Principles in Scala\" by Martin Odersky"
        ));
        edu.add(edu.size(), new SectionContent(
                "03/2011 - 04/2011",
                "Luxoft",
                "",
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""
        ));
        edu.add(edu.size(), new SectionContent(
                "01/2005 - 04/2005",
                "Siemens AG",
                "",
                "3 месяца обучения мобильным IN сетям (Берлин)"
        ));
        edu.add(edu.size(), new SectionContent(
                "09/1997 - 03/1998",
                "Alcatel",
                "",
                "6 месяцев обучения цифровым телефонным сетям (Москва)"
        ));
        edu.add(edu.size(), new SectionContent(
                "09/1993 - 07/1996",
                "Санкт-Петербургский национальный исследовательский университет " +
                        "информационных технологий, механики и оптики",
                "",
                "Аспирантура (программист С, С++)"
        ));
        edu.add(edu.size(), new SectionContent(
                "09/1987 - 07/1993",
                "Санкт-Петербургский национальный исследовательский университет " +
                "информационных технологий, механики и оптики",
                "",
                "Инженер (программист Fortran, C)"
        ));
        edu.add(edu.size(), new SectionContent(
                "09/1984 - 06/1987",
                "Заочная физико-техническая школа при МФТИ",
                "",
                "Закончил с отличием"
        ));
        AbstractSection<List<SectionContent>> educations = new AbstractSection<>(edu);
        sections.put(SectionType.EDUCATION, educations);

        resume.setSections(sections);

        System.out.println(resume.getUuid());
        System.out.println(resume.getFullName());
        System.out.println(resume.getContactList().get(ContactType.PHONE).getContact());
        System.out.println(resume.getContactList().get(ContactType.SKYPE).getContact());
        System.out.println(resume.getContactList().get(ContactType.EMAIL).getContact());
        System.out.println(resume.getContactList().get(ContactType.LINKEDIN).getContact());
        System.out.println(resume.getContactList().get(ContactType.GITHUB).getContact());
        System.out.println(resume.getContactList().get(ContactType.STACKOVERFLOW).getContact());
        System.out.println(resume.getSections().get(SectionType.OBJECTIVE).getInformation());
        System.out.println(resume.getSections().get(SectionType.PERSONAL).getInformation());
        System.out.println(resume.getSections().get(SectionType.ACHIEVEMENT).getInformation());
        System.out.println(resume.getSections().get(SectionType.QUALIFICATION).getInformation());
        System.out.println(resume.getSections().get(SectionType.EXPERIENCE).getInformation());
        System.out.println(resume.getSections().get(SectionType.EDUCATION).getInformation());
    }
}
