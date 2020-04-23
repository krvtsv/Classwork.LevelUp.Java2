package org.levelup.application.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@NoArgsConstructor
@Table(name="user_addresses")
public class UserAddressEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String address;

  //  @ManyToOne
 //   private UserEntity user;

}
