package model;

import java.util.List;

public class UserFollowers {
    private String userName;
    private List<String> followersId;
    private List<Integer> userPosts;

    public UserFollowers(String userName, List<String> followersId, List<Integer> userPosts) {
        this.userName = userName;
        this.followersId = followersId;
        this.userPosts = userPosts;
    }
}
