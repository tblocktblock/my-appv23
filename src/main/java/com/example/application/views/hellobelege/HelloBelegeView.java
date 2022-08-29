package com.example.application.views.hellobelege;

import com.example.application.daten.sqldriver2;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import elemental.json.Json;

import java.io.IOException;
import java.io.InputStream;

@PageTitle("Hello Belege")
@Route(value = "44", layout = MainLayout.class)
@RouteAlias(value = "2", layout = MainLayout.class)

public class HelloBelegeView extends HorizontalLayout {

    private final Button saveDocument;
    public HelloBelegeView() {

        saveDocument = new Button("Speichern");
        saveDocument.setVisible(false);
        MemoryBuffer memoryBuffer = new MemoryBuffer();
        Upload upload = new Upload(memoryBuffer);
        upload.setAcceptedFileTypes("application/pdf", ".pdf");


        upload.addFinishedListener(e -> {
            InputStream inputStream = memoryBuffer.getInputStream();
            // read the contents of the buffered memory
            // from inputStream
            System.out.println("Hallo Welt!");
            saveDocument.setVisible(true);
        });

        saveDocument.addClickListener(e -> {
            String filename = memoryBuffer.getFileName();
           
            byte[] mybytes;
            try {
                mybytes = memoryBuffer.getInputStream().readAllBytes();
                try {
                    sqldriver2.test4(filename, mybytes);
                    Notification.show("gespeichert " + filename);
                    saveDocument.setVisible(false);
                    upload.getElement().setPropertyJson("files", Json.createArray());

                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }        
              
           
                 });

        setMargin(true);
        setVerticalComponentAlignment(Alignment.CENTER,upload,saveDocument );
        VerticalLayout vertical = new VerticalLayout ();
        vertical.add(upload, saveDocument, new H3("Bitte PDF-Dokumente in die Box ziehen!"));
        setVerticalComponentAlignment(Alignment.START, vertical);
        vertical.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
        add(vertical);
        }
}

