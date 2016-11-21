package com.serverutvlab.database.DBLayer;


import com.serverutvlab.business.BModels.BPost;
import com.serverutvlab.business.BModels.BProfile;
import com.serverutvlab.business.BModels.BUser;
import com.serverutvlab.database.DBModels.PostEntity;
import com.serverutvlab.database.DBModels.ProfileEntity;
import com.serverutvlab.database.DBModels.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by o_0 on 2016-11-10.
 */
public class DBFacade {


    /**
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *  BUSER LOGIC CALLS
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */

    static public List<BUser> getAllUsers() {
        List<UserEntity> entities = new UserLogic().getAllUsers();
        List<BUser> result = new ArrayList<BUser>();
        for (UserEntity e: entities)
            result.add(new BUser(e.getId(),e.getEmail(),e.getPassword()));
        return result;
    }

    static public boolean createNewUser(String email, String password) {
        UserEntity user = new UserLogic().createNewAccount(email,password);

        return user != null && user.getId() != 0;
    }

    public static boolean authenticateUser(String e, String p) {
        return new UserLogic().authenticateUser(e, p);
    }

    public static BUser getUserById(int id) {
        UserEntity user = new UserLogic().getUserById(id);
        return new BUser(user.getId(),user.getEmail(),user.getPassword());
    }

    /**
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *  BPROFILE LOGIC CALLS
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */

    static public BProfile getProfileForUserId(int userId) {
        ProfileEntity profile = new ProfileLogic().getProfileByUserId(userId);
        List<BPost> posts = new ArrayList<BPost>();
        if (profile == null){
            return null;
        }
        for (PostEntity p : new PostLogic().getPostsByProfileId(profile.getId())) {
            posts.add(new BPost(p.getId(),p.getSubject(),p.getMessageBody(),p.getTimestamp(),p.getAuthorId(),p.getRecipientId()));
        }

        return new BProfile(
                profile.getId(),
                profile.getName(),
                profile.getInfo(),
                profile.getAge(),
                profile.getRelationshipStatus(),
                profile.getUserId(),
                posts
                );
    }

    /**
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *  BPOST LOCIG CALLS
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */

    static public PostEntity getPostWithId(int id) {
        return null;
    }

    public static List<BPost> getPostsForProfile(int profileId) {
        List<BPost> posts = new ArrayList<BPost>();
        for(PostEntity e : new PostLogic().getPostsByProfileId(profileId)){
            posts.add(new BPost(e.getId(),e.getSubject(),e.getMessageBody(),e.getTimestamp(),e.getAuthorId(),e.getRecipientId()));
        }

        return posts;
    }

    public static BPost postPost(int autoridId, int recipientId, String subject, String messageBody) {
        ProfileEntity postedTo = new ProfileLogic().getProfileById(recipientId);
        PostEntity p = new PostLogic().createPost(autoridId,recipientId,subject,messageBody,postedTo);
        System.out.println("Post returning from PostLogic: " + p);
        return p != null? new BPost(p.getId(),p.getSubject(),p.getMessageBody(),p.getTimestamp(),p.getAuthorId(),p.getRecipientId()) : null;
    }
}
