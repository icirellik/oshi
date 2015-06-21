/**
 * Oshi (https://github.com/dblock/oshi)
 * 
 * Copyright (c) 2010 - 2015 The Oshi Project Team
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * dblock[at]dblock[dot]org
 * alessandro[at]perucchi[dot]org
 * widdis[at]gmail[dot]com
 * https://github.com/dblock/oshi/graphs/contributors
 */
package oshi.software.os.linux;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystemVersion;
import oshi.software.os.linux.proc.OSVersionInfoEx;

/**
 * Linux is a family of free operating systems most commonly used on personal
 * computers.
 *
 * @author alessandro[at]perucchi[dot]org
 */
public class LinuxOperatingSystem implements OperatingSystem {

	private OperatingSystemVersion _version = null;
	private String _family = null;

	public String getFamily() {
		if (_family == null) {
			Scanner in;
			try {
				in = new Scanner(new FileReader("/etc/os-release"));
			} catch (FileNotFoundException e) {
				return "";
			}
			in.useDelimiter("\n");
			while (in.hasNext()) {
				String[] splittedLine = in.next().split("=");
				if (splittedLine[0].equals("NAME")) {
					// remove beginning and ending '"' characters, etc from
					// NAME="Ubuntu"
					_family = splittedLine[1].replaceAll("^\"|\"$", "");
					break;
				}
			}
			in.close();
		}
		return _family;
	}

	public String getManufacturer() {
		return "GNU/Linux";
	}

	public OperatingSystemVersion getVersion() {
		if (_version == null) {
			_version = new OSVersionInfoEx();
		}
		return _version;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getManufacturer());
		sb.append(" ");
		sb.append(getFamily());
		sb.append(" ");
		sb.append(getVersion().toString());
		return sb.toString();
	}
}