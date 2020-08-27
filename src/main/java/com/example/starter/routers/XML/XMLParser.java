package com.example.starter.routers.XML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class XMLParser {
  private class XMLTag {
    public String name;
    public boolean isOpen;

    public XMLTag(String name, boolean isOpen) {
      this.name = name;
      this.isOpen = isOpen;
    }
  }

  public int getTagCount(String xml) {
    int totalCount = 0;
    HashMap<String, Stack<Integer>> openTagsMap = new HashMap<>(); // maps tag to stack of open instances in openTags array
    List<String> openTags = new ArrayList<>(); // list of all open

    int currentTagIndex = 0;
    while ((currentTagIndex = xml.indexOf('<', currentTagIndex)) >= 0) {
      XMLTag tag = getTagAt(xml, currentTagIndex);
      if (tag != null) {
        Stack<Integer> tagInstances = openTagsMap.getOrDefault(tag.name, new Stack<>());
        if (tag.isOpen) {
          tagInstances.push(openTags.size());
          openTags.add(tag.name);
          openTagsMap.put(tag.name, tagInstances);
        } else if (tagInstances.size() > 0) {
          totalCount++;
          // invalidate tags that were open inside
          int openTagsIndex = tagInstances.peek();
          openTags.listIterator(openTagsIndex).forEachRemaining(t -> openTagsMap.get(t).pop());
          openTags = openTags.subList(0, openTagsIndex);
        }
        currentTagIndex += tag.name.length() + 2 +
          (tag.isOpen ? 0 : 1); // 2 for <> 1 for /
      }
    }
    return totalCount;
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
