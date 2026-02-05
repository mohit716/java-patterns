package solid.single_responsibility_principle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

// ---------------------------------------------------------------------------
// SRP Example 3: Article publishing — each class has one job.
// ---------------------------------------------------------------------------
// Part A – Design:
// 5 classes: Article (data), ArticleValidator, ArticleStorage, ArticleNotifier, ArticleLogger.
// Article: store the article details.
// ArticleValidator: validate the article (title, content, authorId).
// ArticleStorage: persist the article to a file.
// ArticleNotifier: send a "published" confirmation to the author.
// ArticleLogger: log the publish event.
// Why one class would violate SRP: One class would have four reasons to change
// (validation rules, file format, notification channel, log format).
// ---------------------------------------------------------------------------

class Article {
    private final String id;
    private final String title;
    private final String content;
    private final String authorId;

    public Article(String id, String title, String content, String authorId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthorId() { return authorId; }
}

class ArticleValidator {
    public boolean isValid(Article article) {
        if (article == null) return false;
        if (article.getTitle() == null || article.getTitle().trim().isEmpty()) return false;
        if (article.getContent() == null || article.getContent().trim().isEmpty()) return false;
        if (article.getAuthorId() == null || article.getAuthorId().trim().isEmpty()) return false;
        return true;
    }
}

class ArticleStorage {
    public void save(Article article) throws IOException {
        Path path = Paths.get("article_" + article.getId() + ".txt");
        String content = String.format("Article %s | Content %s | Author %s",
                article.getTitle(), article.getContent(), article.getAuthorId());
        Files.writeString(path, content);
        System.out.println("Article saved to: " + path.toAbsolutePath());
    }
}

class ArticleNotifier {
    public void sendConfirmation(Article article) {
        System.out.println("--- Confirmation (simulated) ---");
        System.out.println("To author: " + article.getAuthorId());
        System.out.println("Article " + article.getTitle() + " by author " + article.getAuthorId() + " has been published.");
        System.out.println("--- Sent ---");
    }
}

class ArticleLogger {
    public void logPublish(Article article) {
        System.out.println("LOG: Published at " + Instant.now() + " — Article " + article.getId() + " by " + article.getAuthorId());
    }
}

public class ArticlePublisher {
    public static void main(String[] args) throws IOException {
        Article article = new Article("art-1", "Hello World", "This is the content.", "author-42");

        ArticleValidator validator = new ArticleValidator();
        if (!validator.isValid(article)) {
            System.out.println("Article validation failed.");
            return;
        }
        System.out.println("Article validated.");

        ArticleStorage storage = new ArticleStorage();
        storage.save(article);

        ArticleNotifier notifier = new ArticleNotifier();
        notifier.sendConfirmation(article);

        ArticleLogger logger = new ArticleLogger();
        logger.logPublish(article);
    }
}
