package wizardDialog;

import org.eclipse.jface.wizard.Wizard;

public class BookSurveyWizard extends Wizard {
    public static final String Q1 = "QUESTION_1";
    public static final String Q2 = "QUESTION_2";
    public static final String ASK_FOR_CONTACT = "ASK_FOR_CONTACT";
    public static final String EMAIL = "EMAIL";
    public static final String THANKS = "THANKS";
    //声明向导的三个页面
    private QuestionOne one;
    private QuestionTwo two;
    private Thanks thanks;

    public BookSurveyWizard() {
        // 创建三个WizardPage对象
        one = new QuestionOne();
        two = new QuestionTwo();
        thanks = new Thanks();
        // addPage():添加WizardPage到Wizard（添加在最后面）
        this.addPage(one);
        this.addPage(two);
        this.addPage(new AskForContact());
        this.addPage(new Email());
        this.addPage(thanks);
        this.setWindowTitle("读者调查向导");  // 向导标题
        this.setHelpAvailable(true);
    }

    /**
     * canFinish():向导过程是否结束，这里是出现thanks页才结束
     *
     * @return 当前向导页为thanks则为true，否则false
     */
    @Override
    public boolean canFinish() {
        //仅当当前页面为感谢页面时才将“完成”按钮置为可用状态
        return this.getContainer().getCurrentPage() == thanks;
    }

    // 子类必须实现该方法以执行一些特殊的完成程序（在完成时执行特殊的程序）
    public boolean performFinish() {
        return true;
    }
}
