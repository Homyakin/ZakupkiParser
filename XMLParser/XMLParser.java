package XMLParser;

import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

class XMLParser implements AutoCloseable {
	private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();
	private XMLStreamReader reader;

	public XMLParser(InputStream is) throws XMLStreamException {
		reader = FACTORY.createXMLStreamReader(is);
	}

	public XMLStreamReader getReader() {
		return reader;
	}

	public boolean findStartBlock(String parent) throws XMLStreamException {
		while (reader.hasNext()) {
			int event = reader.next();
			if (parent != null && event == XMLEvent.START_ELEMENT && parent.equals(reader.getLocalName())) {
				return true;
			}
		}
		return false;
	}

	public boolean skipBlock() throws XMLStreamException {
		String parent = getName();
		while (reader.hasNext()) {
			int event = reader.next();
			if (parent != null && event == XMLEvent.END_ELEMENT && parent.equals(reader.getLocalName())) {
				return true;
			}
		}
		return false;
	}

	public boolean getNextInBlock(String block) throws XMLStreamException {
		while (reader.hasNext()) {
			int event = reader.next();
			if (block != null && event == XMLEvent.END_ELEMENT && block.equals(reader.getLocalName())) {
				return false;
			}
			if (event == XMLEvent.START_ELEMENT)
				return true;
		}
		return false;
	}

	public String getName() {
		return reader.getLocalName();
	}

	public String getAttribute(String name) throws XMLStreamException {
		return reader.getAttributeValue(null, name);
	}

	public String getText() throws XMLStreamException {
		return reader.getElementText();
	}

	@Override
	public void close() {
		if (reader != null) {
			try {
				reader.close();
			} catch (XMLStreamException e) {
			}
		}
	}

}
