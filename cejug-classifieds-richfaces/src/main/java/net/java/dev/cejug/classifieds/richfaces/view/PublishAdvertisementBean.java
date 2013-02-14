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
package net.java.dev.cejug.classifieds.richfaces.view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsServiceBusiness;
import net.java.dev.cejug_classifieds.metadata.attachments.AtavarImage;
import net.java.dev.cejug_classifieds.metadata.attachments.AvatarImageOrUrl;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.Period;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.Customer;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1216 $ ($Date: 2009-02-17 20:29:09 +0100 (Tue, 17 Feb 2009) $)
 */
public class PublishAdvertisementBean {

	private transient final CejugClassifiedsBusiness SERVICE;

	private Advertisement advertisement = new Advertisement();

	private String avatarType = AvatarType.IMAGE.getType();

	private String selectedTab;

	private Date start;

	private Date finish;

	protected Date getStart() {

		return start;
	}

	protected void setStart(Date start) {

		this.start = start;
	}

	protected Date getFinish() {

		return finish;
	}

	protected void setFinish(Date finish) {

		this.finish = finish;
	}

	public Date getStartDate() {

		return this.getStart();
	}

	public void setStartDate(Date date) {

		this.setStart(date);
	}

	public Date getFinishDate() {

		return this.getFinish();
	}

	public void setFinishDate(Date date) {

		this.setFinish(date);
	}

	public String getSelectedTab() {

		return selectedTab;
	}

	public void setSelectedTab(String selectedTab) {

		this.selectedTab = selectedTab;
	}

	public PublishAdvertisementBean() {

		SERVICE = new CejugClassifiedsServiceBusiness()
				.getCejugClassifiedsBusiness();
		getAdvertisement().setAvatarImageOrUrl(new AvatarImageOrUrl());
	}

	public Advertisement getAdvertisement() {

		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {

		this.advertisement = advertisement;
	}

	public String getAvatarType() {

		return avatarType;
	}

	public void setAvatarType(String avatarImageOrUrl) {

		this.avatarType = avatarImageOrUrl;
	}

	private SelectItem advType = null;

	public SelectItem getAdvType() {

		return advType;
	}

	public void setAdvType(SelectItem advType) {

		this.advType = advType;
	}

	private AdvertisementCategoryWrapper selectedCategory;

	public AdvertisementCategoryWrapper getSelectedCategory() {

		return selectedCategory;
	}

	public void setSelectedCategory(
			AdvertisementCategoryWrapper selectedCategory) {

		this.selectedCategory = selectedCategory;
	}

	/**
	 * The list of the types of advertisements (simple, html, with image, etc..)
	 * 
	 * @return a list of select items containing the advertisement types.
	 */
	public List<SelectItem> getAdvertisementTypes() {

		// The highlander combo raised from hell again :)
		// TODO: to use a cache instead of loading every time...
		List<AdvertisementType> availableTypes = SERVICE
				.readAdvertisementTypeBundleOperation(new BundleRequest())
				.getAdvertisementType();
		List<SelectItem> list = new ArrayList<SelectItem>(availableTypes.size());
		for (AdvertisementType type : availableTypes) {
			list.add(new SelectItem(type, type.getName()));
		}
		if (!list.isEmpty()) {
			advType = list.get(0);
		}
		return list;
	}

	public List<SelectItem> getAvatarTypes() {

		List<SelectItem> list = new ArrayList<SelectItem>();

		for (AvatarType item : AvatarType.values()) {
			list.add(new SelectItem(item.getType(), item.toString()));
		}

		return list;
	}

	public void publish() {

		long domainId = 2L;

		// TODO: remove this list from here as soon we learn how to
		// show combo boxes with custom objects in JSF :)
		List<AdvertisementType> availableTypes = SERVICE
				.readAdvertisementTypeBundleOperation(new BundleRequest())
				.getAdvertisementType();

		advertisement.setCategoryId(getSelectedCategory().getId());

		AdvertisementType type = availableTypes.get(0);

		Principal user = SecurityBean.getUserFromContext();
		String username = null;
		if (user == null) {
			username = "unknown";
		} else {
			username = user.getName();
		}

		Customer customer = new Customer();
		customer.setLogin(username);
		customer.setDomainId(domainId); // TODO Get domain from user
		advertisement.setCustomer(customer);
		advertisement.setTypeId(type.getEntityId());

		// Publishing period
		Calendar startDate = GregorianCalendar.getInstance();
		startDate.setTime(getStart());

		Calendar finishDate = GregorianCalendar.getInstance();
		finishDate.setTime(getFinish());

		Period period = new Period();
		period.setStart(startDate);
		period.setFinish(finishDate);
		advertisement.setPublishingPeriod(period);

		// status
		advertisement.setStatus(3); // ONLINE

		PublishingHeader header = new PublishingHeader();
		header.setCustomerDomainId(domainId);
		header.setCustomerLogin(username);

		// TODO : refactory this :)
		AvatarImageOrUrl avatarImageOrUrl = getAdvertisement()
				.getAvatarImageOrUrl();

		if (avatarType.equals(AvatarType.IMAGE.getType())) {
			if (avatarImageOrUrl == null || avatarImageOrUrl.getImage() == null
					|| avatarImageOrUrl.getImage().getValue() == null) {

				addMessage("Image: value is required.",
						FacesMessage.SEVERITY_ERROR);
				return;
			}
		} else if (avatarType.equals(AvatarType.URL.getType())) {
			try {
				// Get the content type if image is url type.
				URL url = new URL(avatarImageOrUrl.getUrl());
				URLConnection cn = url.openConnection();
				avatarImageOrUrl.setImage(new AtavarImage());
				avatarImageOrUrl.getImage().setContentType(cn.getContentType());
			} catch (Exception e) {
				addMessage("Error:" + e.getMessage(),
						FacesMessage.SEVERITY_ERROR, e);
				return;
			}
		} else if (avatarType.equals(AvatarType.GRAVATAR.getType())) {
			try {
				avatarImageOrUrl.setUrl(".");
				// Get the content type if image is url type.
				avatarImageOrUrl.setImage(new AtavarImage());
				avatarImageOrUrl.getImage().setContentType("image/jpeg");
			} catch (Exception e) {
				addMessage("Error:" + e.getMessage(),
						FacesMessage.SEVERITY_ERROR, e);
				return;
			}
		}

		try {
			SERVICE.publishOperation(advertisement, header);
			clearPublishData();
			addMessage("Publish with success!", FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			addMessage("Error:" + e.getMessage(), FacesMessage.SEVERITY_ERROR,
					e);
		}
	}

	private void clearPublishData() {

		setAdvertisement(new Advertisement());
		setStartDate(null);
		setFinishDate(null);
		getAdvertisement().setAvatarImageOrUrl(new AvatarImageOrUrl());
		setSelectedTab("tab1");
		setAvatarType(AvatarType.IMAGE.getType());
		clearUploadData();
	}

	public static void addMessage(String messageText, Severity typeMessage) {

		addMessage(messageText, typeMessage, null);
	}

	public static void addMessage(String messageText, Severity typeMessage,
			Exception exception) {

		FacesContext context = FacesContext.getCurrentInstance();
		StringBuffer summary = new StringBuffer();
		summary.append(messageText);
		if (exception != null) {
			summary.append(getStackTrace(exception));
		}
		FacesMessage message = new FacesMessage(typeMessage,
				summary.toString(), null);
		context.addMessage(null, message);
	}

	private static String getStackTrace(Exception exception) {

		StringWriter writer = new StringWriter();
		exception.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}

	/* File Upload */
	private List<AtavarImage> files = new ArrayList<AtavarImage>();

	private int uploadsAvailable = 1;

	private boolean autoUpload = true;

	private boolean useFlash = false;

	public int getSize() {

		return getFiles().size();
	}

	public void paint(OutputStream stream, Object object) throws IOException {

		synchronized (object) {
			if (object instanceof String) {
				stream.write(getFiles()
						.get(Integer.parseInt(object.toString())).getValue());
			} else {
				stream.write(getFiles().get((Integer) object).getValue());
			}
		}

	}

	/*
	 * public void listener(UploadEvent event) { synchronized (event) {
	 * UploadItem item = event.getUploadItem(); try {
	 * getAdvertisement().setAvatarImageOrUrl(new AvatarImageOrUrl());
	 * getAdvertisement().getAvatarImageOrUrl().setImage( new AtavarImage());
	 * getAdvertisement().getAvatarImageOrUrl().getImage().setValue(
	 * item.getData());
	 * 
	 * // TODO: I thing need change this for run fine in LINUX SO. String
	 * fileName = item.getFileName(); fileName =
	 * fileName.substring(fileName.lastIndexOf('\\') + 1);
	 * 
	 * getAdvertisement().getAvatarImageOrUrl().setName(fileName);
	 * getAdvertisement().getAvatarImageOrUrl().setDescription( fileName);
	 * getAdvertisement().getAvatarImageOrUrl().getImage()
	 * .setContentType(item.getContentType());
	 * 
	 * files.add(getAdvertisement().getAvatarImageOrUrl().getImage());
	 * setUploadsAvailable(getUploadsAvailable() - 1); } catch (Exception e) {
	 * e.printStackTrace(); } } }
	 */

	public String clearUploadData() {

		files.clear();
		setUploadsAvailable(1);
		return null;
	}

	public long getTimeStamp() {

		return System.currentTimeMillis();
	}

	public List<AtavarImage> getFiles() {

		return files;
	}

	public void setFiles(List<AtavarImage> files) {

		this.files = files;
	}

	public int getUploadsAvailable() {

		return uploadsAvailable;
	}

	public void setUploadsAvailable(int uploadsAvailable) {

		this.uploadsAvailable = uploadsAvailable;
	}

	public boolean isAutoUpload() {

		return autoUpload;
	}

	public void setAutoUpload(boolean autoUpload) {

		this.autoUpload = autoUpload;
	}

	public boolean isUseFlash() {

		return useFlash;
	}

	public void setUseFlash(boolean useFlash) {

		this.useFlash = useFlash;
	}

}
