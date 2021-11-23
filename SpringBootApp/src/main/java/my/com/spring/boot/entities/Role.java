package my.com.spring.boot.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Role extends AuditEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(unique = true)
	@NotNull(message = "Please provider a role name !")
	private String name;
	@ManyToMany(
        fetch = FetchType.LAZY,
        cascade =  {
                CascadeType.PERSIST,
                CascadeType.MERGE
        },
        mappedBy = "roles"
	)
	private Set<User> users = new HashSet<>();
	
	public Role() {
		
	}

	public Role(Date createAt, Date updateAt, String createBy,String updateBy,String deleteBy, String name) {
		super.setCreateAt(createAt);
		super.setUpdateAt(updateAt);
		super.setCreateBy(createBy);
		super.setUpdateBy(updateBy);
		super.setDeleteBy(deleteBy);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
