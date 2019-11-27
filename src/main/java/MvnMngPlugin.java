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
        String selectedText = caretModel.getCurrentCaret().getSelectedText().trim()+".plist";

        String path = "C:\\Users\\KAVI\\Desktop\\"+selectedText;

        File file = new File(path);
        boolean MvnMng = false;
        if(file.exists())
        {
            try {
                Scanner sc = new Scanner(file);
                while(sc.hasNext())
                {
                    String inp[] = sc.nextLine().split(" = ");
                    if(inp[0].trim().equals("IsMvnMngSupported"))
                    {
                        String match = (inp[1].charAt(0)=='"') ? inp[1].substring(1,inp[1].length()-2) : inp[1].substring(0 , inp[1].length()- 1);
                        MvnMng = match.equals("True");
                        break;
                    }
                }

            } catch (FileNotFoundException ex) {
                MvnMng = false;
            }
        }
        String message = MvnMng ? "MvnMng is supported" : "MvnMng is not supported";
        HintManager.getInstance().showInformationHint(editor , message);
    }
}
