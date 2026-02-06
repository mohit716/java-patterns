class Article:
    def __init__(self, id, title, content, authorId):
        self.id = id
        self.title = title
        self.content = content
        self.authorId = authorId
    def getId(self):
        return self.id
    def getTitle(self):
        return self.title
    def getContent(self):
        return self.content
    def getAuthorId(self):
        return self.authorId

class ArticleValidator:
    def isValid(self, article):
        if article is None:
            return False
        if article.getTitle() is None or article.getTitle().strip() == '':
            return False
        if article.getContent() is None or article.getContent().strip() == '':
            return False
        if article.getAuthorId() is None or article.getAuthorId().strip() == '':
            return False
        return True

class ArticleStorage:
    def save(self, article):
        with open(f'article_{article.getId()}.txt', 'w') as f:
            f.write(f'Article {article.getId()} | Title {article.getTitle()} | Content {article.getContent()} | Author {article.getAuthorId()}')
class ArticleNotifier:
    def sendConfirmation(self, article):
        print(f'Article {article.getId()} by author {article.getAuthorId()} has been published.')
class ArticleLogger:
    def logPublish(self, article):
        print(f'Article {article.getId()} by author {article.getAuthorId()} has been published.')

