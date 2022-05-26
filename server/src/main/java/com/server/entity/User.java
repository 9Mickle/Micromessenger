package com.server.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    @ToString.Exclude
    private Set<Chat> chats;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public void addChat(Chat chat) {
        this.chats.add(chat);
    }

    public void removeChat(Chat chat) {
        this.chats.remove(chat);
        chat.getUsers().remove(this);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @PreRemove
    private void deleteUser() {
        this.getChats().forEach(chat -> chat.getUsers().remove(this));
    }
}
