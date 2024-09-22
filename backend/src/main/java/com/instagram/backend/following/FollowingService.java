package com.instagram.backend.following;

import com.instagram.backend.config.JwtService;
import com.instagram.backend.follower.Follower;
import com.instagram.backend.follower.FollowerRepository;
import com.instagram.backend.user.User;
import com.instagram.backend.user.UserRepository;
import com.instagram.backend.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowingService {
    private final FollowingRepository followingRepository;
    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public FollowingResponse follow(FollowingRequest followingRequest,  HttpServletRequest request) {

        //get the jwt token from the request header
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwtToken = authorizationHeader.split(" ")[1];
        String email = jwtService.extractUsername(jwtToken);
        //find the logged in user by email in JWT token
        Optional<User> user = userRepository.findByEmail(email);
        Optional<User> followingUser = userRepository.findById(followingRequest.getFollowId());
        //update the follower table
        Following following = Following.builder()
               .user(user.get())
                .followingId(followingUser.get().getId())
                .followingEmail(followingUser.get().getEmail())
                .followingName(followingUser.get().getName())
                .followingProfilePicUrl(followingUser.get().getImg())
               .build();
        followingRepository.save(following);


        //update the follower table
        Follower follower = Follower.builder()
               .user(followingUser.get())
               .followerId(user.get().getId())
               .followerEmail(user.get().getEmail())
               .followerName(user.get().getName())
               .followerProfilePicUrl(user.get().getImg())
               .build();
        followerRepository.save(follower);

        //return the updated user details
        Optional<User> resultUser = userRepository.findByEmail(email);
        //return the updated following user details
        Optional<User> resultFollower = userRepository.findById(followingRequest.getFollowId());

        //discard the password from the response
        resultUser.get().setPassword(null);
        resultFollower.get().setPassword(null);

        FollowingResponse followingResponse = FollowingResponse.builder()
               .LoggedInUser(resultUser.get())
               .CurrentPageUser(resultFollower.get())
               .build();
        return  followingResponse;
    }

    public FollowingResponse unfollow(FollowingRequest followingRequest,  HttpServletRequest request) {
        //get the jwt token from the request header
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwtToken = authorizationHeader.split(" ")[1];
        String email = jwtService.extractUsername(jwtToken);
        //find the logged in user by email in JWT token
        Optional<User> user = userRepository.findByEmail(email);
        Optional<User> followingUser = userRepository.findById(followingRequest.getFollowId());

        //update user's following table
        var deletedFollowing = followingRepository.findByUserIdAndFollowingId(user.get().getId(), followingUser.get().getId());

        //update following's follower table
        var deletedFollower = followerRepository.findByUserIdAndFollowerId(followingUser.get().getId(), user.get().getId());
        followingRepository.delete(deletedFollowing);
        followerRepository.delete(deletedFollower);
        //return the updated user details
        Optional<User> resultUser = userRepository.findByEmail(email);
        Optional<User> resultFollower = userRepository.findById(followingRequest.getFollowId());

        //discard the password from the response
        resultUser.get().setPassword(null);
        resultFollower.get().setPassword(null);

        FollowingResponse followingResponse = FollowingResponse.builder()
               .LoggedInUser(resultUser.get())
                .CurrentPageUser(resultFollower.get())
                .build();
        return  followingResponse;
    }

}
