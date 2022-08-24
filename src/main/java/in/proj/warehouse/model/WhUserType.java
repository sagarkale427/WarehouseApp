package in.proj.warehouse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="wh_user_type_tab")
@NoArgsConstructor
@AllArgsConstructor
public class WhUserType {

	@Id
	@Column(name="wh_usr_id_col")
	@GeneratedValue(generator="whusr_gen")
	@SequenceGenerator(sequenceName="whusr_seq", name="whusr_gen")
	private Integer id;
	@Column(name="wh_usr_type_col")
	private String userType;
	@Column(name="wh_usr_code_col")
	private String userCode;
	@Column(name="wh_usr_for_col")
	private String userFor;
	@Column(name="wh_usr_email_col")
	private String userEmail;
	@Column(name="wh_usr_contact_col")
	private String userContact;
	@Column(name="wh_usr_id_type_col")
	private String userIdType;
	@Column(name="wh_usr_other_col")
	private String ifOther;
	@Column(name="wh_usr_id_num_col")
	private String userIdNum;
	
	
	
	
}
