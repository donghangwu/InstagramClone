package com.instagram.backend.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.instagram.backend.follower.Follower;
import com.instagram.backend.following.Following;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(exclude = {"followers","following"})
//@ToString(exclude = {"followers","following"})

@Entity
@Table(name="_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password;
    private String img;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<Following> following;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<Follower> followers;

//    @ManyToMany(mappedBy = "followers", cascade = CascadeType.ALL)
//    private Set<User> following=new HashSet<>();
//
//    @ManyToMany
//    @JoinTable(name = "followers", joinColumns = @JoinColumn(name = "follower_id"), inverseJoinColumns = @JoinColumn(name = "followed_id"))
//    private Set<User> followers=new HashSet<>();

//    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "following")
//    private Set<User> followers = new HashSet<>();
//
//    @JoinTable(name = "followers",
//            joinColumns = {@JoinColumn(name = "user_id")},
//            inverseJoinColumns = {@JoinColumn(name = "follower_id")})
//    @ManyToMany(cascade = CascadeType.ALL)
//    private Set<User> following = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", img='" + img + '\'' +
//                ", role=" + role +
//                '}';
//    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
}
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
