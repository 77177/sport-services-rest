package org.application.models.users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.application.models.requests.RoomRequest;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, exclude = "roomRequests")
public class Trainer extends AppUser {

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requester")
    private List<RoomRequest> roomRequests;
}
