package com.example.ensai.gloin;

import org.w3c.dom.Element;

import java.util.List;

/**
 * Created by ensai on 13/05/16.
 */
public interface IElementDAO {

    public void sauvegarderElements(List<Element> elements);

    public List<Element> chargerElements();

    public void ajouterElement(Element element);
}
