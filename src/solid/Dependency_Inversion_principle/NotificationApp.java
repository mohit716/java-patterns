// File: NotificationApp.java
// this is the interface that will be implemented by the concrete classes
interface MessageService {
    String getMessage();
}
// email service implements the message service interface means it will have the getMessage method
class EmailService implements MessageService {
    public String getMessage() {
        return "Email message sent!";
    }
}
// this is the orchestrator class that will use the message service to send the message
class NotificationManager {
    private final MessageService service;

    // Constructor injection
    public NotificationManager(MessageService service) {
        this.service = service;
    }

    public void notify() {
        System.out.println(service.getMessage());
    }
}
// this is the main class that will use the notification manager to send the message
public class NotificationApp {
    public static void main(String[] args) {
        MessageService service = new EmailService();
        NotificationManager manager = new NotificationManager(service);
        manager.notify();
    }
}

/*
Redo:

MessageService is the interface that will be implemented by the concrete classes

We have EmailService that implements the MessageService interface

there could have bee more but we have only one for now



when a interface is implemented it must implement all the methods of the interface


if we didnt used notifaction manager we would have to create the email service in the main class
like this:
public class Main{
    public static void main(String[] args) {
        MessageService service = new EmailService();
        service.sendMessage("Hello, how are you?");
    }
}

Questionstion comes why this is bad?
Answer is that we are directly creating the email service in the main class
and why is creating the email service in the main class bad?


Because it tightly couples your high-level code (Main / app entrypoint) 
to a low-level detail (EmailService), which breaks the Dependency Inversion Principle.


What is being tightly coupled mean?
Tightly coupled means that the two classes are dependent on each other and cannot be used independently.

How our bad eg cant be used independently?

In the “bad” example, Main is not independent because it depends on a specific concrete class:

Main directly does new EmailService(), so Main can’t run without EmailService being present.
If you want to switch to SMSService (or any other service), you must edit Main (so Main can’t be reused as-is).
For testing, you can’t easily plug in a fake/mock service; Main is forced to use the real EmailService.
So Main and EmailService are “tightly coupled” because Main is “stuck” with that exact implementation.


If we see in my language main class more constant than variable
i.e we cant plug and play any service we want to use

So we can’t easily swap services (Email, SMS, etc.) without changing Main



Is this thing also applicable in cases like ai models? when we want our script to use different ai models?
we dont use specific ai models we make it configurable. 


And is the configurable thing is also a dependency inversion?
Often yes, if you do it the DIP way:

DIP part: your high-level code depends on an abstraction 
(e.g., LLMClient / ModelProvider interface), not on OpenAIClient directly.
Configurable part: configuration just decides which implementation to inject
 at runtime (via a factory/DI/wiring code).
So: configuration supports DIP, but configuration alone isn’t automatically 
DIP unless you also introduce the interface/abstraction + injection.
*/

/*
Notification manageger is the object creater

and uses the interface in a way 

that any concrete implementation can be passed to it

kind of support for plug and play

*/