package com.serverutvlab.business;

import com.serverutvlab.business.BModels.BPost;
import com.serverutvlab.business.BModels.BProfile;
import com.serverutvlab.business.BModels.BUser;
import com.serverutvlab.services.SModels.SPost;
import com.serverutvlab.services.SModels.SProfile;
import com.serverutvlab.services.SModels.SUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj on 2016-11-17.
 */
public class BFacade {

    /**
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *  USER SERVICE CALLS
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */

    /**
     * calls for fetching all the users in database
     * @return List of convertes BUsers to SUsers
     */
    public static List<SUser> getAllUsers(){
        List<SUser> result = new ArrayList<SUser>();
        List<BUser> users = new BUserLogic().getAllUsers();
        for (BUser u: users)
            result.add(new SUser(u.getId(),u.getEmail()));
        return result;
    }

    /**
     * call to fetch user by id
     * @param id id of the user to be fetched
     * @return converted BUser to SUser
     */
    public static SUser getUserById(int id) {
        BUser user = new BUserLogic().getUserById(id);
        return new SUser(user.getId(),user.getEmail());
    }

    /**
     * calls for authentication of a user
     * @param email email
     * @param password password
     * @return true if authentication is a success, false otherwise
     */
    public static boolean authenticateUser(String email, String password) {
        return new BUserLogic().authenticateUser(email,password);
    }

    /**
     * calls for register new user with
     * @param email email
     * @param password password
     * @return true if registration was a success, false otherwise
     */
    public static boolean registerUser(String email, String password) {
        return new BUserLogic().createUser(email, password);
    }


    /**
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *  PROFILE SERVICE CALLS
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */

    /**
     * calls to fetch the profile for a user
     * @param userId userId
     * @return BProfile converted To SProfile
     */
    public static SProfile getProfileById(int userId){
        BProfile profile = new BProfileLogic().getProfileForUser(userId);
        List<SPost> posts = new ArrayList<SPost>();
        for (BPost p : profile.getWallPosts()) {
            posts.add(new SPost(p.getId(),p.getSubject(),p.getMessageBody(),p.getTimestamp(),p.getAuthorId(),p.getRecipientId()));
        }

        return new SProfile(
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
     *  POST SERVICE CALLS
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */


}
