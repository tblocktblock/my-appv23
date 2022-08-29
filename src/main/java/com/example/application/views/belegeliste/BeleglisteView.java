package com.example.application.views.belegeliste;

import com.example.application.daten.Belege;
import com.example.application.daten.sqldriver2;
import com.example.application.views.MainLayout;
import com.vaadin.componentfactory.pdfviewer.PdfViewer;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;

@PageTitle("Belegliste")
@Route(value = "Belegliste", layout = MainLayout.class)
@RouteAlias(value = "1", layout = MainLayout.class)

public class BeleglisteView extends VerticalLayout {
    private Boolean anzeige;
    private String saveresult;
    private Grid<Belege> grid = new Grid<>(Belege.class, false);
    Binder<Belege> binder = new Binder<>(Belege.class);
    public BeleglisteView() {

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);
        add(new H2("Belegliste für den Mandanten -- KGM Johannes"),splitLayout);

        String suche ="" ;
        ArrayList<Belege> belegListe = new ArrayList<Belege>();

        try {
            belegListe = sqldriver2.test2(suche) ;

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        for (Belege belege : belegListe) {
            System.out.println("Beleg: " + belege.getDateiname() + " " + belege.getAblagedatum()+ " " + belege.getIndexx() + belege.getAngeordnet_richtig());
            System.out.println((belege.getAblagedatum().toString()));
        }
        System.out.println("Suche: " + suche +" hallo");
        System.out.println(System.getProperty("java.runtime.version"));

        grid.addColumn(Belege::getRechnerisch_richtig).setHeader("Index").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(Belege::getDateiname ).setHeader("Beleg").setSortable(true).setResizable(true).setWidth("200px").setFlexGrow(0);;
        grid.addColumn(Belege::getAblagedatum ).setHeader("Ablagedatum").setSortable(true).setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(Belege::getMandant ).setHeader("Mandant").setSortable(false).setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(Belege::getAbrechnungsobjekt).setHeader("AbrObj.").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(Belege::getSachkonto ).setHeader("Sachkonto").setSortable(false).setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(Belege::getVerwendungszweck ).setHeader("Verwendungszweck").setSortable(false).setAutoWidth(true).setFlexGrow(0).setTextAlign(ColumnTextAlign.START);
        grid.addColumn(Belege::getRechnungsbetrag ).setHeader("Rechnungsbetrag").setSortable(false).setAutoWidth(true).setFlexGrow(0);

        grid.addColumn(Belege::getAngeordnet_richtig).setHeader("Angeordnet").setAutoWidth(true).setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);
        grid.setItems(belegListe);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        grid.setHeight("650px");

        grid.addSelectionListener(this::selectionChange);
    }

    private  TextField Indexx = new TextField("Index");
    private  TextField mandatennummer = new TextField("Mandantennummer");
    private TextField verwendungszweck = new TextField("Verwendungszweck");
    private ComboBox<String> abrechnungsobjekt = new ComboBox<>("Abrechnungsobjekt");
    private   ComboBox<String> sachkonto = new ComboBox<>("Sachkonto");
    private NumberField rechnungsbetrag_brutto = new NumberField();
    private Button showDocument = new Button("show Document");
    private Button deleteDocument = new Button("delete Document");
    private Button saveDocument = new Button("save Document");



    private void createEditorLayout (SplitLayout splitLayout) {

        anzeige = false;
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("800px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");;
        editorLayoutDiv.add(editorDiv);

        TextField Dateiname = new TextField("Belegnummer:");
        TextField Ablagedatum = new TextField("Ablagedatum:");

        abrechnungsobjekt.setItems("220010","220011");
        sachkonto.setItems("688000000","69410000");
        rechnungsbetrag_brutto.setLabel("Rechnungsbetrag - Brutto");
        mandatennummer.setPlaceholder("46100");

        editorDiv.add(createFormLayout());

        editorLayoutDiv.add( new H2("This place intentionally right empty"));
        splitLayout.addToSecondary(editorLayoutDiv);

        binder.forField(mandatennummer)
                // Validator defined based on a lambda
                // and an error message
                .withValidator(
                        name -> name.length() >= 3,
                        "Name must contain at least three characters")
                .bind(Belege::getMandant, Belege::setMandant);

        binder.forField(abrechnungsobjekt)
                // Validator defined based on a lambda
                // and an error message
                .withValidator(
                        name -> name.length() >= 3,
                        "Name must contain at least three characters")
                .bind(Belege::getAbrechnungsobjekt, Belege::setAbrechnungsobjekt);

        binder.forField(sachkonto)
                // Validator defined based on a lambda
                // and an error message
                .withValidator(
                        name -> name.length() >= 3,
                        "Name must contain at least three characters")
                .bind(Belege::getSachkonto, Belege::setSachkonto);

        binder.forField(verwendungszweck)
                // Validator defined based on a lambda
                // and an error message
                .withValidator(
                        name -> name.length() >= 3,
                        "Name must contain at least three characters")
                .bind(Belege::getVerwendungszweck, Belege::setVerwendungszweck);

        binder.forField(Ablagedatum)
                // Validator defined based on a lambda
                // and an error message
                .bind(Belege::getAblagedatum, Belege::setAblagedatum);

        binder.forField(Indexx)
                // Validator defined based on a lambda
                // and an error message
                .bind(Belege::getIndexx, Belege::setIndexx);


        binder.forField(rechnungsbetrag_brutto)
                // Validator defined based on a lambda
                // and an error message
                .bind(Belege::getRechnungsbetrag, Belege::setRechnungsbetrag);


        showDocument.addClickListener(e -> {
            //formLayout.setVisible(false);
            if (!anzeige) {
                System.out.printf("Hallo");
                createGridLayout2(splitLayout, Indexx.getValue());
                anzeige = (true);
                showDocument.setText("show list");}
            else{
                createGridLayout(splitLayout);
                anzeige = (false);
                showDocument.setText("show document");}

        });

        saveDocument.addClickListener(e -> {
            System.out.printf("click Button speichern"+ "" +Indexx.getValue()+ " " + mandatennummer.getValue());

            try {

            saveresult =   sqldriver2.test9(Indexx.getValue(),mandatennummer.getValue(), abrechnungsobjekt.getValue(),sachkonto.getValue(), verwendungszweck.getValue(),rechnungsbetrag_brutto.getValue()) ;

            } catch (Exception e1) {
                e1.printStackTrace();
            }
            UI.getCurrent().getPage().reload();

        });

        deleteDocument.addClickListener(e -> {
            //formLayout.setVisible(false);
        });
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");
        formLayout.add(showDocument, mandatennummer,abrechnungsobjekt,sachkonto, verwendungszweck,rechnungsbetrag_brutto,saveDocument,deleteDocument);

        formLayout.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1));
        return formLayout;
    }
    private void createGridLayout(SplitLayout splitLayout) {

        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void createGridLayout2(SplitLayout splitLayout, String suchebeleg) {

        String tempdatei = "";

        try {
            tempdatei = sqldriver2.test8(suchebeleg) ;
        } catch (Exception e) {

            e.printStackTrace();
        }

        Path path = Paths.get(tempdatei);
        long bytes;
        bytes = 0;
        try {
            bytes = Files.size(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.printf("Suche Beleg: " + tempdatei +"//" +  bytes +"//" + path.toString());

        setSizeFull();
        //setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(CENTER);
        getStyle().set("text-align", "center");

        String finalTempdatei = tempdatei;

        PdfViewer pdfViewer = new PdfViewer();

        final StreamResource fileResource = new StreamResource("MyResourceName", () -> {
            try
            {

                //return new FileInputStream(new File("/tmp/rechnung16858125075991091914.pdf"));
                return new FileInputStream(new File(finalTempdatei));
            }
            catch(final FileNotFoundException e)
            {
                e.printStackTrace();
                return null;
            }
        });


        pdfViewer.setSrc(fileResource);
        pdfViewer.setHeight(800, Unit.PIXELS);

        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(pdfViewer);
    }

    private void selectionChange(SelectionEvent<Grid<Belege>, Belege> selection) {

        Optional<Belege> optionalBelege = selection.getFirstSelectedItem();

        if (optionalBelege.isPresent())
        {
            //binder = new BeanValidationBinder<>(Belege.class);
            //binder.bindInstanceFields(this);
            binder.readBean(optionalBelege.get());
            Notification.show("gesucht: " + optionalBelege.get().getDateiname() + " Index: "+ optionalBelege.get().getIndexx());
        }
    }
}
