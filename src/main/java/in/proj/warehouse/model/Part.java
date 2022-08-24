package in.proj.warehouse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="part_tab")
public class Part {
	
	@Id
	@GeneratedValue(generator="part_gen")
	@SequenceGenerator(name ="part_gen",sequenceName="part_seq")
	@Column(name="part_id_col")
	private Integer id;
	
	@Column(name="part_code_col")
	private String partCode;
	
	@Column(name="part_curr_col")
	private String partCurrency;
	
	@Column(name="part_cost_col")
	private Double partBaseCost;
	
	@Column(name="part_wid_col")	
	private String partWid;
	
	@Column(name="part_ht_col")
	private String partHt;
	
	@Column(name="part_len_col")
	private String partLen;
	
	@Column(name="part_desc_col")
	private String partDesc;
	
	
	
	
	
	
	

}
