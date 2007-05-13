/*
 * {{{ header & license
 * Copyright (c) 2007 Sean Bright
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */
package org.xhtmlrenderer.simple.extend.form;

import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import org.w3c.dom.Element;
import org.xhtmlrenderer.simple.extend.XhtmlForm;

public class RadioButtonField extends InputField {
    private static final String FS_DEFAULT_GROUP = "__fs_default_group_";
    
    private static int _defaultGroupCount = 1;
    
    public RadioButtonField(Element e, XhtmlForm form) {
        super(e, form);
    }
    
    private static String createNewDefaultGroup() {
        return FS_DEFAULT_GROUP + ++_defaultGroupCount;
    }

    public JComponent create() {
        JToggleButton radio = new JRadioButton();

        radio.setText("");
        radio.setOpaque(false);
        
        String groupName = null;

        if (hasAttribute("name")) {
            if (getAttribute("name").trim().length() > 0) {
                groupName = getAttribute("name");
            }
        } else {
            groupName = createNewDefaultGroup();
        }

        // Add to the group for mutual exclusivity
        getParentForm().addButtonToGroup(groupName, radio);

        return radio;
    }
    
    public void applyOriginalState() {
        JToggleButton button = (JToggleButton) getComponent();
        
        button.setSelected(getOriginalState().isChecked());
    }
    
    protected String[] getFieldValues() {
        JToggleButton button = (JToggleButton) getComponent();
        
        if (button.isSelected()) {
            return new String [] {
                    hasAttribute("value") ? getAttribute("value") : "" 
            };
        } else {
            return new String [] {};
        }
    }
}