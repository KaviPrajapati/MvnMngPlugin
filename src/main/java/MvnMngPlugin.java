import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MvnMngPlugin extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        String selectedText = caretModel.getCurrentCaret().getSelectedText().trim().toLowerCase() + ".plist";

        String path = "/Users/kavi.prajapati/IntelliJTask/modules/" + selectedText;

        File file = new File(path);
        boolean MvnMng = false;
        boolean fileexist = false;
        if (file.exists()) {
            fileexist = true;
            try {
                Scanner sc = new Scanner(file);
                while (sc.hasNext()) {
                    String inp[] = sc.nextLine().split(" = ");
                    if (inp[0].trim().equals("IsMvnMgrSupported")) {
                        String match = (inp[1].charAt(0) == '"') ? inp[1].substring(1, inp[1].length() - 2) : inp[1].substring(0, inp[1].length() - 1);
                        MvnMng = match.equals("True");
                        break;
                    }
                }

            } catch (FileNotFoundException ex) {
                MvnMng = false;
            }
        }
        String message = !fileexist ? "This is not a dependency" : (MvnMng ? "MvnMgr is supported" : "MvnMgr is not supported");
        HintManager.getInstance().showInformationHint(editor, message);
    }

    @Override
    public void update(AnActionEvent e) {

        String filename = e.getData(CommonDataKeys.PSI_FILE).getName();

        boolean POM = false;

        if(filename.equals("pom.xml"))
            POM = true;

        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        e.getPresentation().setEnabledAndVisible(POM && caretModel.getCurrentCaret().hasSelection());
    }
}
