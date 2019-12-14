package wizardDialog;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class AskForContact extends WizardPage {
    private Button no;
    private Button yes;

    protected AskForContact() {
        super(BookSurveyWizard.ASK_FOR_CONTACT, "您可以提供联系方式吗？", ImageDescriptor.createFromFile(QuestionOne.class, "q.gif"));
        this.setMessage("如果您留下联系方式，将有机会参加抽奖活动。");
    }

    //判断是否需要输入联系方式
    public boolean canContact() {
        return yes.getSelection() == true;
    }

    @Override
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));
        new Label(composite, SWT.LEFT).setText("A.");
        no = new Button(composite, SWT.RADIO);
        no.setText("不，我不想留联系方式");
        no.setSelection(true);
        new Label(composite, SWT.LEFT).setText("B.");
        yes = new Button(composite, SWT.RADIO);
        yes.setText("恩，我愿意留下联系方式");
        setControl(composite);
    }

    /**
     * email是nextPage,thanks是下下一个页面,需要用getPage()获取
     *
     * @return 用户按下next后显示的向导页
     */
    public IWizardPage getNextPage() {
        // 如果留下联系方式，则返回下一页
        if (canContact())
            return super.getNextPage();
        // 否则返回感谢页面
        return getWizard().getPage(BookSurveyWizard.THANKS);
    }
}
