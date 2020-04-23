package org.levelup.application.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    private Integer id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;
    @Column(length = 50, nullable = false, unique = true)
    private String passport;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private List<UserAddressEntity> addresses;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private AuthDetailsEntity authDetails;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passport='" + passport + '\'' +
                '}';
    }

}

