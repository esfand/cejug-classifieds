/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 CEJUG - Ceará Java Users Group
 
 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.
 
 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.
 
 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 
 This file is part of the CEJUG-CLASSIFIEDS Project - an  open source classifieds system
 originally used by CEJUG - Ceará Java Users Group.
 The project is hosted https://cejug-classifieds.dev.java.net/
 
 You can contact us through the mail dev@cejug-classifieds.dev.java.net
 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
package net.java.dev.cejug.classifieds.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.java.dev.cejug_classifieds.metadata.admin.OperationTimestamp;

/**
 * @author $Author: felipegaucho $
 * @version $Rev: 578 $ ($Date: 2008-09-03 19:58:27 +0200 (Wed, 03 Sep 2008) $)
 */
@Entity
@Table(name = "RESPONSE_TIME")
public class OperationTimestampEntity extends
		AbstractEntity<OperationTimestamp> {

	// TODO falta modelar essa tabela

	@Column(nullable = false)
	private String operationName;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;

	@Column(nullable = false)
	private Long responseTime;

	@Column(nullable = false)
	private Boolean status;

	@Column(nullable = false)
	private String clientId;

	@Column(nullable = true)
	private String fault;

	public String getOperationName() {

		return operationName;
	}

	public void setOperationName(final String operationName) {

		this.operationName = operationName;
	}

	public Calendar getDate() {

		return date;
	}

	public void setDate(final Calendar date) {

		this.date = date;
	}

	public Long getResponseTime() {

		return responseTime;
	}

	public void setResponseTime(final Long responseTime) {

		this.responseTime = responseTime;
	}

	public Boolean getStatus() {

		return status;
	}

	public void setStatus(final Boolean status) {

		this.status = status;
	}

	public String getClientId() {

		return clientId;
	}

	public void setClientId(final String clientId) {

		this.clientId = clientId;
	}

	public String getFault() {

		return fault;
	}

	public void setFault(final String fault) {

		this.fault = fault;
	}

}
