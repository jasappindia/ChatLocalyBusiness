
package com.chatlocalybusiness.reportsTable;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.activity.PaymentStatsActivity;
import com.chatlocalybusiness.apiModel.PaymentStatsModel;
import com.chatlocalybusiness.apiModel.QuarterlyPaymentStatsModel;
import com.chatlocalybusiness.apiModel.StatsByCustomerModel;
import com.chatlocalybusiness.reportsTable.model.Cell;
import com.chatlocalybusiness.reportsTable.model.ColumnHeader;
import com.chatlocalybusiness.reportsTable.model.RowHeader;
import com.chatlocalybusiness.reportsTable.popup.TableViewAdapter;
import com.chatlocalybusiness.utill.Constants;
import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.filter.Filter;
import com.evrencoskun.tableview.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public static final int COLUMN_SIZE = 3;
    public static int ROW_SIZE = 12;
    String cornerName;
    private List<RowHeader> mRowHeaderList;
    private List<ColumnHeader> mColumnHeaderList;
    private List<List<Cell>> mCellList;

    private AbstractTableAdapter mTableViewAdapter;
    private TableView mTableView;
    private Filter mTableFilter; // This is used for filtering the table.
    private Pagination mPagination; // This is used for paginating the table.

    private PaymentStatsActivity mainActivity;

    // Columns indexes
    public static final int MOOD_COLUMN_INDEX = 3;
    public static final int GENDER_COLUMN_INDEX = 4;

    List<PaymentStatsModel.PaymentStat> paymentStatsMonthList;

    // Constant values for icons
    public static final int SAD = 0;
    public static final int HAPPY = 1;
    public static final int BOY = 0;
    public static final int GIRL = 1;

    private boolean paginationEnabled = false;
    private QuarterlyPaymentStatsModel.PaymentStats paymentStatsQuarterly;
    private List<StatsByCustomerModel.PaymentStat> paymentStatsCustomer;

    public boolean isPaginationEnabled() {
        return paginationEnabled;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (PaymentStatsActivity) getActivity();
        getValues();
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_table, container, false);

        RelativeLayout fragment_container = (RelativeLayout) view.findViewById(R.id.fragment_container);

        // Create Table view
        mTableView = createTableView();

        if (paginationEnabled) {
            mTableFilter = new Filter(mTableView); // Create an instance of a Filter and pass the
            // created TableView.

            // Create an instance for the TableView pagination and pass the created TableView.
            mPagination = new Pagination(mTableView);

            // Sets the pagination listener of the TableView pagination to handle
            // pagination actions. See onTableViewPageTurnedListener variable declaration below.
            mPagination.setOnTableViewPageTurnedListener(onTableViewPageTurnedListener);
        }
        fragment_container.addView(mTableView);
//        calcEmiAllMonths(amountBorrowed,timePeriods,interestRate);
        loadData();
        return view;
    }

    public void getValues() {
        Bundle b = getArguments();
        if (b != null) {
            paymentStatsMonthList = (List<PaymentStatsModel.PaymentStat>) b.getSerializable(Constants.MONTHLY_STATS);
            paymentStatsQuarterly = (QuarterlyPaymentStatsModel.PaymentStats) b.getSerializable(Constants.QUARTER_STATS);
            paymentStatsCustomer = (List<StatsByCustomerModel.PaymentStat>) b.getSerializable(Constants.CUSTOMER_STATS);
        }
        if (paymentStatsMonthList != null) {
            ROW_SIZE = 12;
            cornerName ="Month";
        } else if (paymentStatsQuarterly != null) {
            ROW_SIZE = 4;
            cornerName="Quarter";
        } else if (paymentStatsCustomer != null) {
            ROW_SIZE = paymentStatsCustomer.get(0).getCustomers().size();
           cornerName="Customer";
        }
    }

    private TableView createTableView() {
        TableView tableView = new TableView(getContext());

        // Set adapter
        mTableViewAdapter = new TableViewAdapter(getContext(),cornerName );
        tableView.setAdapter(mTableViewAdapter);

        // Disable shadow
        //tableView.getSelectionHandler().setShadowEnabled(false);

        // Set layout params
        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        tableView.setLayoutParams(tlp);

        // Set TableView listener
        //   tableView.setTableViewListener(new TableViewListener(tableView));
        return tableView;
    }


    private void initData() {
        mRowHeaderList = new ArrayList<>();
        mColumnHeaderList = new ArrayList<>();
        mCellList = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            mCellList.add(new ArrayList<Cell>());
        }
    }

    private void loadData() {
        List<RowHeader> rowHeaders = getRowHeaderList();
//        List<List<Cell>> cellList = getCellListForSortingTest(); // getCellList();
        List<List<Cell>> cellList = getCellList(); // getCellList();
        List<ColumnHeader> columnHeaders = getColumnHeaderList(); //getRandomColumnHeaderList(); //

        mRowHeaderList.addAll(rowHeaders);
        for (int i = 0; i < cellList.size(); i++) {
            mCellList.get(i).addAll(cellList.get(i));
        }
        // Load all data
        mColumnHeaderList.addAll(columnHeaders);
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);

    }

    String[] monthArray = {"Jan", "Fab", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String[] quarterArray = {"Quarter1", "Quarter2", "Quarter3", "Quarter4"};

    private List<RowHeader> getRowHeaderList() {
        List<RowHeader> list = new ArrayList<>();
        if (paymentStatsMonthList != null) {
            for (int i = 0; i < ROW_SIZE; i++) {
                RowHeader header = new RowHeader(String.valueOf(i), monthArray[i]);
                list.add(header);
            }
        } else if (paymentStatsQuarterly != null) {
            for (int i = 0; i < ROW_SIZE; i++) {
                RowHeader header = new RowHeader(String.valueOf(i), quarterArray[i]);
                list.add(header);
            }
        } else {
            for (int i = 0; i < ROW_SIZE; i++) {
                RowHeader header = new RowHeader(String.valueOf(i), paymentStatsCustomer.get(0).getCustomers().get(i).getCFullName());
                list.add(header);
            }

        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    public static List<RowHeader> getRowHeaderList(int startIndex) {
        List<RowHeader> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            RowHeader header = new RowHeader(String.valueOf(i), "row " + (startIndex + i));
            list.add(header);
        }

        return list;
    }

    String[] columnList = {"SALES", "TAX", "EARNINGS"};

    private List<ColumnHeader> getColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();

        for (int i = 0; i < COLUMN_SIZE; i++) {

            ColumnHeader header = new ColumnHeader(String.valueOf(i), columnList[i]);
            list.add(header);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    private List<ColumnHeader> getRandomColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();

        for (int i = 0; i < COLUMN_SIZE; i++) {
            String title = "column " + i;
            int nRandom = new Random().nextInt();
            if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
                title = "large column " + i;
            }

            ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
            list.add(header);
        }

        return list;
    }

    String text;

    private List<List<Cell>> getCellList() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < COLUMN_SIZE; j++) {
               if(paymentStatsMonthList!=null)
                text= getMonthCellData(j,i);
               if(paymentStatsQuarterly!=null)
                   text= getQuarterCellData(j,i);
               if(paymentStatsCustomer!=null)
                   text= getCustomerCellData(j,i);


                String id = j + "-" + i;

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }

    public String  getMonthCellData(int j,int i)
    {
        if(j==0)
            text = paymentStatsMonthList.get(i).getSumofTotal();
        if(j==1)
            text = paymentStatsMonthList.get(i).getSumofTax();
        if(j==2)
            text = paymentStatsMonthList.get(i).getSumofEarnings();
        return text;
    }
    public String  getQuarterCellData(int j,int i)
    {
        if(i==0) {
            if (j == 0)
                text = paymentStatsQuarterly.getQuarter1().getSumofTotal();
            if (j == 1)
                text = paymentStatsQuarterly.getQuarter1().getSumofTax();
            if (j == 2)
                text = paymentStatsQuarterly.getQuarter1().getSumofEarnings();
          }
          else if(i==1)
        {
            if (j == 0)
                text = paymentStatsQuarterly.getQuarter2().getSumofTotal();
            if (j == 1)
                text = paymentStatsQuarterly.getQuarter2().getSumofTax();
            if (j == 2)
                text = paymentStatsQuarterly.getQuarter2().getSumofEarnings();

        }
          else if(i==2)
        {
            if (j == 0)
                text = paymentStatsQuarterly.getQuarter3().getSumofTotal();
            if (j == 1)
                text = paymentStatsQuarterly.getQuarter3().getSumofTax();
            if (j == 2)
                text = paymentStatsQuarterly.getQuarter3().getSumofEarnings();

        }
          else if(i==3)
        {
            if (j == 0)
                text = paymentStatsQuarterly.getQuarter4().getSumofTotal();
            if (j == 1)
                text = paymentStatsQuarterly.getQuarter4().getSumofTax();
            if (j == 2)
                text = paymentStatsQuarterly.getQuarter4().getSumofEarnings();

        }

        return text;
    }
    public String  getCustomerCellData(int j,int i)
    {
        if(j==0)
            text = paymentStatsCustomer.get(0).getCustomers().get(i).getSumofTotal();
        if(j==1)
            text = paymentStatsCustomer.get(0).getCustomers().get(i).getSumofTax();
        if(j==2)
            text =  paymentStatsCustomer.get(0).getCustomers().get(i).getSumofEarnings();
        return text;
    }

    /**
     * This is a dummy model list test some cases.
     */
    private List<List<Cell>> getCellListForSortingTest() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < COLUMN_SIZE; j++) {
                Object text = "cell " + j + " " + i;

                final int random = new Random().nextInt();
                if (j == 0) {
                    text = i;
                } else if (j == 1) {
                    text = random;
                } else if (j == MOOD_COLUMN_INDEX) {
                    text = random % 2 == 0 ? HAPPY : SAD;
                } else if (j == GENDER_COLUMN_INDEX) {
                    text = random % 2 == 0 ? BOY : GIRL;
                }

                // Create dummy id.
                String id = j + "-" + i;

                Cell cell;
                if (j == 3) {
                    cell = new Cell(id, text, random % 2 == 0 ? "happy" : "sad");
                } else if (j == 4) {
                    // NOTE female and male keywords for filter will have conflict since "female"
                    // contains "male"
                    cell = new Cell(id, text, random % 2 == 0 ? "boy" : "girl");
                } else {
                    cell = new Cell(id, text);
                }
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    private List<List<Cell>> getRandomCellList() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            list.add(cellList);
            for (int j = 0; j < COLUMN_SIZE; j++) {
                String text = "cell " + j + " " + i;
                int random = new Random().nextInt();
                if (random % 2 == 0 || random % 5 == 0 || random == j) {
                    text = "large cell  " + j + " " + i + getRandomString() + ".";
                }

                // Create dummy id.
                String id = j + "-" + i;

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    public static List<List<Cell>> getRandomCellList(int startIndex) {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            list.add(cellList);
            for (int j = 0; j < COLUMN_SIZE; j++) {
                String text = "cell " + j + " " + (i + startIndex);
                int random = new Random().nextInt();
                if (random % 2 == 0 || random % 5 == 0 || random == j) {
                    text = "large cell  " + j + " " + (i + startIndex) + getRandomString() + ".";
                }

                String id = j + "-" + (i + startIndex);

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
        }

        return list;
    }


    private static String getRandomString() {
        Random r = new Random();
        String str = " a ";
        for (int i = 0; i < r.nextInt(); i++) {
            str = str + " a ";
        }

        return str;
    }

    public void filterTable(String filter) {
        // Sets a filter to the table, this will filter ALL the columns.
        mTableFilter.set(filter);
    }

    public void filterTableForMood(String filter) {
        // Sets a filter to the table, this will only filter a specific column.
        // In the example data, this will filter the mood column.
        mTableFilter.set(3, filter);
    }

    public void filterTableForGender(String filter) {
        // Sets a filter to the table, this will only filter a specific column.
        // In the example data, this will filter the gender column.
        mTableFilter.set(4, filter);
    }

    // The following four methods below: nextTablePage(), previousTablePage(),
    // goToTablePage(int page) and setTableItemsPerPage(int itemsPerPage)
    // are for controlling the TableView pagination.
    public void nextTablePage() {
        mPagination.nextPage();
    }

    public void previousTablePage() {
        mPagination.previousPage();
    }

    public void goToTablePage(int page) {
        mPagination.goToPage(page);
    }

    public void setTableItemsPerPage(int itemsPerPage) {
        mPagination.setItemsPerPage(itemsPerPage);
    }

    // Handler for the changing of pages in the paginated TableView.
    private Pagination.OnTableViewPageTurnedListener onTableViewPageTurnedListener =
            new Pagination.OnTableViewPageTurnedListener() {
                @Override
                public void onPageTurned(int numItems, int itemsStart, int itemsEnd) {
                    int currentPage = mPagination.getCurrentPage();
                    int pageCount = mPagination.getPageCount();
                    mainActivity.previousButton.setVisibility(View.VISIBLE);
                    mainActivity.nextButton.setVisibility(View.VISIBLE);

                    if (currentPage == 1 && pageCount == 1) {
                        mainActivity.previousButton.setVisibility(View.INVISIBLE);
                        mainActivity.nextButton.setVisibility(View.INVISIBLE);
                    }

                    if (currentPage == 1) {
                        mainActivity.previousButton.setVisibility(View.INVISIBLE);
                    }

                    if (currentPage == pageCount) {
                        mainActivity.nextButton.setVisibility(View.INVISIBLE);
                    }

//                    mainActivity.tablePaginationDetails
//                            .setText(mainActivity.getString(R.string.table_pagination_details, String.valueOf(currentPage), String.valueOf(itemsStart), String.valueOf(itemsEnd)));

                }
            };
}
