package org.spingarda;

import org.springframework.context.annotation.Scope;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Ivan on 19/11/2016.
 */
@Service
@Scope("prototype")
public class BotSpam {

    private boolean running = false;

    private String log;

    public void run(Facebook facebook, BotParams params) throws Exception {
        log = "";

        String pageId = params.getPage();

        Long delay = params.getDelay() == null ? 10000 : params.getDelay();

        Integer postAmount = params.getPostsAmount();

        Integer commentAmount = params.getCommentsAmount();

        setRunning(true);

        publishOnPosts(pageId, postAmount, commentAmount, facebook, delay);

        setRunning(false);

        log += "Done.\n";
    }

    private void publishOnPosts(String pageId, Integer postAmount, Integer commentAmount, Facebook facebook, Long delay) throws Exception {
        logInfo("Opening comments file");

        List<String> phrases = Files.readAllLines(Paths.get("", "comments.txt"));

        logInfo("Obtaining page posts list " + pageId);

        List<Post> posts = facebook.feedOperations().getFeed(pageId);

        logInfo(posts.size() + " posts found");

        int publishedPosts = 0;
        for (Post post : posts) {
            logInfo("Sending a comments to " + post.getId());
            publishComments(post, commentAmount, phrases, facebook, delay);

            if (postAmount != null) {
                publishedPosts++;
                if (publishedPosts >= postAmount) {
                    logInfo("Required posts amount published");
                    break;
                }
            }
        }
    }

    private void publishComments(Post post, Integer commentAmount, List<String> phrases, Facebook facebook, Long delay) throws Exception {

        int publishedComments = 0;
        do {
            String comment = getRandomLine(phrases);
            logComment("Publishing the logComment: " + comment);
            try {
                facebook.commentOperations().addComment(post.getId(), comment);
            } catch (Exception e) {
                logError("The comment has be blocked by Facebook: " + e.getMessage());
            }
            publishedComments++;

            Thread.sleep(delay);
        } while (commentAmount != null && publishedComments < commentAmount);
    }

    private String getRandomLine(List<String> phrases) {
        int random = (int) (Math.random() * phrases.size());
        return phrases.get(random);
    }

    private void logError(String message) {
        log += "ERROR: " + message + "\n";
    }

    private void logInfo(String message) {
        log += "INFO: " + message + "\n";
    }

    private void logComment(String message) {
        log += "COMMENT: " + message + "\n";
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
