package org.rf.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Role generated by hbm2java
 */
@Entity
@Table(name = "role")
public class Role implements java.io.Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8168693900200590599L;
	private String id;
	private String nama;
	private String keterangan;
	private Set<Users> userses = new HashSet<Users>(0);

	public static final String ROLE_ADMINISTRATOR = "1";
	public static final String ROLE_MANAGER = "2";
	public static final String ROLE_APPROVER = "3";
	public static final String ROLE_UPLOADER = "4";
	public static final String ROLE_USER = "5";

	public Role() {
	}

	public Role(String nama, String keterangan, Set<Users> userses) {
		this.nama = nama;
		this.keterangan = keterangan;
		this.userses = userses;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 * @author Roberto
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the id to set
	 * @author Roberto
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAMA", length = 100)
	public String getNama() {
		return this.nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@Column(name = "KETERANGAN", length = 250)
	public String getKeterangan() {
		return this.keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	public Set<Users> getUserses() {
		return this.userses;
	}

	public void setUserses(Set<Users> userses) {
		this.userses = userses;
	}

}
