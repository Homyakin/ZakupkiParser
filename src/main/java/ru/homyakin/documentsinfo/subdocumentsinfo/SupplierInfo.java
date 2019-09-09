package ru.homyakin.documentsinfo.subdocumentsinfo;

import java.util.Optional;

public class SupplierInfo {
	private String name;
	private Optional<String> shortName;
	private Optional<String> INN;
	private String type;
	private boolean provider;
	private boolean nonResident;

	public SupplierInfo(String name, String shortName, String INN, String type, boolean provider,
						boolean nonResident) {
		this.name = name;
		this.shortName = Optional.ofNullable(shortName);
		this.INN = Optional.ofNullable(INN);
		this.nonResident = nonResident;
		this.type = type;
		this.provider = provider;
	}

	public String getName() {
		return name;
	}

	public Optional<String> getShortName() {
		return shortName;
	}

	public Optional<String> getINN() {
		return INN;
	}

	public String getType() {
		return type;
	}

	public boolean isProvider() {
		return provider;
	}

	public boolean isNonResident() {
		return nonResident;
	}

}
