package com.example.starter.routers.XML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class XMLParser {
  private class XMLTag {
    String name;
    boolean isOpen;

    XMLTag(String name, boolean isOpen) {
      this.name = name;
      this.isOpen = isOpen;
    }

    int getLengthWith() {
      return name.length() + 2 +
        (isOpen ? 0 : 1); // 2 for <> 1 for
    }
  }

  private ArrayList<XMLTag> initialParseXml(String xml) {
    int currentTagIndex = 0;
    ArrayList<XMLTag> tagsFound = new ArrayList<>();
    while ((currentTagIndex = xml.indexOf('<', currentTagIndex)) >= 0) {
      XMLTag tag = getTagAt(xml, currentTagIndex);
      if (tag != null) {
        tagsFound.add(tag);
        currentTagIndex += tag.getLengthWith();
      }
    }
    return tagsFound;
  }

  public int getTagCount(String xml) {
    int validTagsFound = 0;
    HashMap<String, Stack<Integer>> openTagsMap = new HashMap<>(); // maps tag to stack of open instances in openTags array
    List<String> openTags = new ArrayList<>(); // list of all open

    ArrayList<XMLTag> parsedXML = initialParseXml(xml);
    for (XMLTag tag : parsedXML) {
      Stack<Integer> tagInstances = openTagsMap.getOrDefault(tag.name, new Stack<>());
      if (tag.isOpen) {
        tagInstances.push(openTags.size());
        openTags.add(tag.name);
        openTagsMap.put(tag.name, tagInstances);
      } else if (tagInstances.size() > 0) {
        validTagsFound++;
        // invalidate tags that were open inside
        int openTagsIndex = tagInstances.peek();
        openTags.listIterator(openTagsIndex).forEachRemaining(t -> openTagsMap.get(t).pop());
        openTags = openTags.subList(0, openTagsIndex);
      }
    }
    return validTagsFound;
  }

  private XMLTag getTagAt(String xml, int index) {
    if (xml.charAt(index) == '<') {
      boolean isOpen = (xml.charAt(index + 1) != '/');
      int tagIndex = isOpen ? index + 1 : index + 2;
      int closeIndex = xml.indexOf('>', index);
      if (closeIndex == -1) {
        return null; // no closing tag
      }
      return new XMLTag(xml.substring(tagIndex, closeIndex), isOpen);
    }
    return null;

  }
}
