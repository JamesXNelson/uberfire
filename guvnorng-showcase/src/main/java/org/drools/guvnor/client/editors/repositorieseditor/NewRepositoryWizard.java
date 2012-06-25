/*
 * Copyright 2005 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.guvnor.client.editors.repositorieseditor;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.drools.guvnor.client.common.FormStylePopup;
import org.drools.guvnor.client.resources.ShowcaseImages;
import org.drools.guvnor.vfs.VFSService;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.Caller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

@Dependent
public class NewRepositoryWizard extends FormStylePopup {

    @Inject
    Caller<VFSService>   vfsService;
    
    private static ShowcaseImages         images           = GWT.create( ShowcaseImages.class );

    private TextBox nameTextBox = new TextBox();
    //private TextArea descriptionTextArea = new TextArea();
    private TextBox descriptionTextArea = new TextBox();
    private TextBox usernameTextBox = new TextBox();
    private TextBox passwordTextBox = new TextBox();
    
    public NewRepositoryWizard() {
        super(images.backupLarge(), "New Repository");

        nameTextBox.setTitle("New Repository Name");
        nameTextBox.setWidth("200px");
        addAttribute("New Repository Name:", nameTextBox);
        
        //descriptionTextArea.setSize("200px", "120px");
        descriptionTextArea.setWidth("200px");
        descriptionTextArea.setTitle("Description");
        addAttribute("Description:", descriptionTextArea);
        

        usernameTextBox.setWidth("200px");
        usernameTextBox.setTitle("User Name");
        addAttribute("User Name:", usernameTextBox);
        
        passwordTextBox.setWidth("200px");
        passwordTextBox.setTitle("Password");
        addAttribute("Password:", passwordTextBox);
        
        HorizontalPanel hp = new HorizontalPanel();
        Button create = new Button("Create");
        create.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                
                    vfsService.call(new RemoteCallback<Void>() {
                        @Override
                        public void callback(final Void v) {
                            Window.alert("The repository is created successfully");
                            hide();
                        }
                    }).createJGitFileSystem(nameTextBox.getText(), descriptionTextArea.getText(),
                            usernameTextBox.getText(), passwordTextBox.getText());
               
            }
        });
        hp.add(create);

        Button cancel = new Button("Cancel");
        cancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                hide();
            }
        });
        hp.add(new HTML("&nbsp"));
        hp.add(cancel);
        addAttribute("", hp);
    }

}