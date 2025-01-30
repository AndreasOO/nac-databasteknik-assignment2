package View;

import Model.Entity.Category;
import Model.Entity.ShopItem;
import Model.ShopModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.stream.Collectors;

public class ShopView implements OrderObserver, SearchResultObserver, FilterResultObserver, UserObserver {
    private final ShopModel shopModel;

    private final JFrame frame;
    private final JPanel mainPanel;

    private final JPanel topPanel;
    private final JLabel searchLabel;
    private final JTextField searchField;
    private final JPanel radioButtonPanel;
    private final ButtonGroup buttonGroup;
    private final JRadioButton radioButtonItemName;
    private final JRadioButton radioButtonItemSize;
    private final JLabel filterLabel;
    private final JComboBox<String> filterComboBox;
    private final JButton logOutButton;
    private final JLabel usernameTopPanelLabel;


    private final JPanel centerPanel;
    private final JPanel searchResultPanel;
    private final JTable searchResultTable;
    private final DefaultTableModel searchResultTableModel;
    private final JScrollPane searchResultScrollPane;

    private final JPanel orderMainPanel;
    private final JPanel orderTopPanel;
    private final JButton addToOrderButton;
    private final JPanel orderCenterPanel;

    private final JPanel orderTablePanel;
    private final JTable orderTable;
    private final DefaultTableModel orderTableModel;
    private final JScrollPane orderTableScrollPane;

    private final JPanel orderSummaryPanel;

    private final TextField orderSummaryShippingAddressTextField;
    private final TextField orderSummaryZipCodeTextField;
    private final TextField orderSummaryTotalCostTextField;
    private final JButton orderSummaryCompleteOrderButton;
    private final JButton orderSummaryRemoveOrderButton;


    // login panel
    private final JPanel loginPanel;
    private final JPanel loginBox;
    private final JTextField usernameTextField;
    private final JPasswordField passwordField;
    private final JButton loginButton;





    public ShopView(ShopModel model) {
        this.shopModel = model;
        shopModel.registerOrderObserver(this);
        shopModel.registerSearchResultObserver(this);
        shopModel.registerFilterResultObserver(this);
        shopModel.registerUserObserver(this);

        frame = new JFrame();
        mainPanel = new JPanel();

        topPanel = new JPanel();
        searchLabel = new JLabel("Search  ", SwingConstants.RIGHT);
        searchField = new JTextField();
        radioButtonPanel = new JPanel();
        buttonGroup  = new ButtonGroup();
        radioButtonItemName = new JRadioButton("Name");
        radioButtonItemSize = new JRadioButton("Size");
        filterLabel = new JLabel("Filter    ", SwingConstants.RIGHT);
        filterComboBox = new JComboBox<>(new String[]{"None",
                                                      "Sandals",
                                                      "Running shoes",
                                                      "Ladies shoes",
                                                      "Men's shoes",
                                                      "Walking shoes",
                                                      "Slim shoes",
                                                      "Crocs"});

        logOutButton = new JButton("Log out");
        usernameTopPanelLabel = new JLabel();


        centerPanel = new JPanel();
        searchResultPanel = new JPanel();
        searchResultTableModel = new DefaultTableModel();
        searchResultTable = new JTable(searchResultTableModel);
        searchResultScrollPane = new JScrollPane(searchResultTable);
        searchResultTableModel.addColumn("Name");
        searchResultTableModel.addColumn("Brand");
        searchResultTableModel.addColumn("Size");
        searchResultTableModel.addColumn("Categories");
        searchResultTableModel.addColumn("Price");
        searchResultTableModel.addColumn("Quantity");


        orderMainPanel = new JPanel();
        orderTopPanel = new JPanel();
        addToOrderButton = new JButton("Add item to order");
        orderCenterPanel = new JPanel();


        orderTablePanel = new JPanel();
        orderTableModel = new DefaultTableModel();
        orderTable = new JTable(orderTableModel);
        orderTableScrollPane = new JScrollPane(orderTable);
        orderTableModel.addColumn("Name");
        orderTableModel.addColumn("Brand");
        orderTableModel.addColumn("Size");
        orderTableModel.addColumn("Price");

        orderSummaryPanel = new JPanel();
        orderSummaryShippingAddressTextField = new TextField();
        orderSummaryZipCodeTextField = new TextField();
        orderSummaryTotalCostTextField = new TextField();
        orderSummaryTotalCostTextField.setEditable(false);
        orderSummaryCompleteOrderButton = new JButton("Complete Order");
        orderSummaryRemoveOrderButton = new JButton("Remove Order");

        loginPanel = new JPanel();
        loginBox = new JPanel();
        usernameTextField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");


    }

    public void init() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(mainPanel);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);


        radioButtonItemName.setSelected(true);
        buttonGroup.add(radioButtonItemName);
        buttonGroup.add(radioButtonItemSize);
        radioButtonPanel.setLayout(new GridLayout(2, 1));
        radioButtonPanel.add(radioButtonItemName);
        radioButtonPanel.add(radioButtonItemSize);

        topPanel.setLayout(new GridLayout(1,7));
        topPanel.add(logOutButton);
        topPanel.add(usernameTopPanelLabel);
        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(radioButtonPanel);
        topPanel.add(filterLabel);
        topPanel.add(filterComboBox);

        centerPanel.setLayout(new GridLayout(2,1));
        centerPanel.add(searchResultPanel);
        centerPanel.add(orderMainPanel);

        searchResultPanel.setLayout(new GridLayout(1,1));
        searchResultPanel.add(searchResultScrollPane);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        searchResultTable.setDefaultRenderer(Object.class, centerRenderer);


        orderMainPanel.setLayout(new BorderLayout());
        orderMainPanel.add(orderTopPanel, BorderLayout.NORTH);
        orderTopPanel.setLayout(new GridLayout(1,1));
        orderTopPanel.add(addToOrderButton, BorderLayout.NORTH);

        orderTablePanel.setLayout(new GridLayout(1,1));
        orderTablePanel.add(orderTableScrollPane);
        DefaultTableCellRenderer centerRenderer2 = new DefaultTableCellRenderer();
        centerRenderer2.setHorizontalAlignment(JLabel.CENTER);
        orderTable.setDefaultRenderer(Object.class, centerRenderer2);

        orderSummaryPanel.setLayout(new GridLayout(8,1));
        orderSummaryPanel.add(new JLabel("Shipping Address"));
        orderSummaryPanel.add(orderSummaryShippingAddressTextField);
        orderSummaryPanel.add(new JLabel("Zip Code"));
        orderSummaryPanel.add(orderSummaryZipCodeTextField);
        orderSummaryPanel.add(new JLabel("Total cost"));
        orderSummaryPanel.add(orderSummaryTotalCostTextField);
        orderSummaryPanel.add(orderSummaryCompleteOrderButton);
        orderSummaryPanel.add(orderSummaryRemoveOrderButton);



        orderMainPanel.add(orderCenterPanel);
        orderCenterPanel.setLayout(new GridLayout(1,2));
        orderCenterPanel.add(orderTablePanel);
        orderCenterPanel.add(orderSummaryPanel);




        loginPanel.setLayout(new GridLayout(3,3));
        loginPanel.add(new JPanel());
        loginPanel.add(new JPanel());
        loginPanel.add(new JPanel());
        loginPanel.add(new JPanel());
        loginPanel.add(loginBox);
        loginPanel.add(new JPanel());
        loginPanel.add(new JPanel());
        loginPanel.add(new JPanel());
        loginPanel.add(new JPanel());
        loginBox.setLayout(new GridLayout(6,1));
        loginBox.add(new JLabel("Username"));
        loginBox.add(usernameTextField);
        loginBox.add(new JLabel("Password"));
        loginBox.add(passwordField);
        loginBox.add(loginButton);


    }

    public void showUserLoginView() {
        mainPanel.removeAll();
        mainPanel.add(loginPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void showCustomerShopView() {
        mainPanel.removeAll();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.removeAll();
        centerPanel.add(searchResultPanel);
        centerPanel.add(orderMainPanel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }


    public void addItemRowToSearchTable(ShopItem shopItem) {
        searchResultTableModel.addRow(new String[]{
                                                   shopItem.getName(),
                                                   shopItem.getBrand(),
                                                   String.valueOf(shopItem.getSize()),
                                                   shopItem.getShoeCategoriesList().stream().map(Category::getDisplayName).collect(Collectors.joining(", ")),
                                                   String.valueOf(shopItem.getPrice()),
                                                   String.valueOf(shopItem.getQuantity())
        });
    }


    public void addItemToOrderTable(ShopItem shopItem) {
        orderTableModel.addRow(new String[]{
                                            shopItem.getName(),
                                            shopItem.getBrand(),
                                            String.valueOf(shopItem.getSize()),
                                            String.valueOf(shopItem.getPrice())
        });
    }

    @Override
    public void updateOrder() {
        resetOrderTable();
        shopModel.getCurrentOrder().forEach(this::addItemToOrderTable);
        int orderPriceSum = shopModel.getCurrentOrder().stream().mapToInt(ShopItem::getPrice).sum();
        orderSummaryTotalCostTextField.setText(String.valueOf(orderPriceSum));
    }

    @Override
    public void updateSearchResult() {
        resetSearchTable();
        shopModel.getCurrentSearchResult().forEach(this::addItemRowToSearchTable);
    }
    @Override
    public void updateFilterResult() {
        resetSearchTable();
        shopModel.getFilteredSearchResult().forEach(this::addItemRowToSearchTable);
    }

    @Override
    public void updateLoggedInUser() {
        usernameTopPanelLabel.setText("      " + shopModel.getUserLoggedIn().getName());
    }

    public void resetSearchTable() {
        searchResultTableModel.setRowCount(0);
    }

    public void resetSearchParameters() {
        filterComboBox.setSelectedIndex(0);
        searchField.setText("");
    }

    public void resetOrderTable() {
        orderTableModel.setRowCount(0);
    }

    public void resetLoginForm() {
        usernameTextField.setText("");
        passwordField.setText("");
    }

    public void resetUserLoggedIn() {
        usernameTextField.setText("");
    }

    public void resetOrderSummary() {
        orderSummaryShippingAddressTextField.setText("");
        orderSummaryZipCodeTextField.setText("");
        orderSummaryTotalCostTextField.setText("");
    }


    public int getSelectedItemInSearchTable() {
        return searchResultTable.getSelectedRow();
    }


    public JTextField getSearchField() {
        return searchField;
    }

    public JRadioButton getRadioButtonItemName() {
        return radioButtonItemName;
    }

    public JRadioButton getRadioButtonItemSize() {
        return radioButtonItemSize;
    }

    public JComboBox<String> getFilterComboBox() {
        return filterComboBox;
    }


    public JButton getAddToOrderButton() {
        return addToOrderButton;
    }

    public JButton getRemoveOrderButton() {
        return orderSummaryRemoveOrderButton;
    }

    public JButton getCompleteOrderButton() {
        return orderSummaryCompleteOrderButton;
    }


    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getLogOutButton() {
        return logOutButton;
    }

    public String getInputUsername() {
        return usernameTextField.getText();
    }

    public String getInputPassword() {
        StringBuilder sb = new StringBuilder();
        sb.append(passwordField.getPassword());
        return sb.toString();
    }

    public void showLoginErrorMessage() {
        JOptionPane.showMessageDialog(frame, "Incorrect username or password, please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    }

}
