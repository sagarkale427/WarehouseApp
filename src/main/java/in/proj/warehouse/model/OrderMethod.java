package in.proj.warehouse.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="order_method_tab")
@AllArgsConstructor
@NoArgsConstructor
public class OrderMethod {

	@Id
	@GeneratedValue(generator="om_gen")
	@SequenceGenerator(name="om_gen",sequenceName="om_sql_seq")
	@Column(name="om_id_col")
	private Integer id;
	
	@Column(name="om_mode_col",unique=true,nullable=false,length=20)
	private String orderMode;
	
	@Column(name="om_code_col")
	private String orderCode;
	
	@Column(name="om_type_col")
	private String orderType;
	
	@Column(name="om_desc_col")
	private String orderDesc;
	
	@ElementCollection
	@CollectionTable(name="om_acpt_tab",
	                 joinColumns=@JoinColumn(name="om_id_col")
	                 )
	@Column(name="om_acpt_col")
	private Set<String> orderAcpt;
}
