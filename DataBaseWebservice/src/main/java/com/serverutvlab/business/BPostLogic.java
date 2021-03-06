package com.serverutvlab.business;

import com.serverutvlab.business.BModels.BPost;
import com.serverutvlab.business.BModels.BProfile;
import com.serverutvlab.business.BModels.BUser;
import com.serverutvlab.database.DBLayer.DBFacade;
import com.serverutvlab.database.DBModels.ProfileEntity;
import com.serverutvlab.services.SModels.SPost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cj on 2016-11-18.
 */
public class BPostLogic {

    /**
     * get posts for profile,
     * @param selectedUserId the user that owns tha profile
     * @param visitorId the user that wants to look at it
     * @return returning only posts that the visiting user are allowed to se
     */
    public List<BPost> getProfilePostsPostsByUserId(int selectedUserId, int visitorId) {
        List<BPost> result = new ArrayList<BPost>();
        List<BPost> posts = DBFacade.getProfilePosts(selectedUserId);
        //BProfile activeUserProfile = DBFacade.getProfileForUserId(activeUserId);

        for (BPost p: posts){
            if (p.isPrivate()){
                if (visitorId == p.getAuthorId() || visitorId == p.getRecipientId()){
                    if (!result.contains(p)){
                        result.add(p);
                    }
                }
            } else {
                if (!result.contains(p)){
                    result.add(p);
                }
            }
        }
        Collections.sort(result);

        return result;
    }

    /**
     * posts a post
     * @param autoridId
     * @param recipientId
     * @param subject
     * @param messageBody
     * @param isPrivate
     * @return
     */
    public BPost postPost(int autoridId, int recipientId, String subject, String messageBody, boolean isPrivate) {
        BPost p = DBFacade.postPost(autoridId,recipientId,subject,messageBody,isPrivate);
        return p;
    }

    /**
     * get post feed for a user
     * @param userId
     * @return returning all the posts that the user is allowed to se
     */
    public List<BPost> getFeedForUser(int userId) {
        List<BPost> feed = new ArrayList<BPost>();
        feed.addAll(DBFacade.getProfilePosts(userId));
        System.out.println("Feed count after adding own posts: " + feed.size());

        List<BUser> friends = DBFacade.getFriendsByUserId(userId);




        for (BUser f: friends){
            for (BPost post: DBFacade.getProfilePosts(f.getId())) {
                if (!feed.contains(post) && !post.isPrivate())
                    feed.add(post);
            }
        }

        System.out.println("Feed count after adding friends posts: " + feed.size());

        Collections.sort(feed);

        return feed;
    }
}
