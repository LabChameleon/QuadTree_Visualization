/**
 * Created by Julian on 11.03.2015.
 */
public class QuadTree {

        private Object lContent;
        private QuadTree lTopLeftTree, lTopRightTree, lBottomLeftTree, lBottomRightTree;

        public QuadTree() {
            lContent = null;
            lTopLeftTree = null;
            lTopRightTree = null;
            lBottomLeftTree = null;
            lBottomRightTree = null;
        }

        public QuadTree(Object pObject) {
            if (pObject!=null) {
                lContent = pObject;
                lTopLeftTree = new QuadTree();
                lTopRightTree = new QuadTree();
                lBottomLeftTree = new QuadTree();
                lBottomRightTree = new QuadTree();
            }
            else {
                lContent = null;
                lTopLeftTree = null;
                lTopRightTree = null;
                lBottomLeftTree = null;
                lBottomRightTree = null;
            }
        }

        public QuadTree(Object pContent, QuadTree pTopLeftTree, QuadTree pTopRightTree, QuadTree pBottomLeftTree, QuadTree pBottomRightTree){
            if (pContent!=null) {
                lContent = pContent;
                if (pTopLeftTree!=null)
                    lTopLeftTree = pTopLeftTree;
                else
                    lTopLeftTree = new QuadTree();
                if (pTopRightTree!=null)
                    lTopRightTree = pTopRightTree;
                else
                    lTopRightTree = new QuadTree();
                if(pBottomLeftTree != null)
                    lBottomLeftTree = pBottomLeftTree;
                else
                    lBottomLeftTree = new QuadTree();
                if(pBottomRightTree != null)
                    lBottomRightTree = pBottomRightTree;
                else
                    lBottomRightTree = new QuadTree();

            }
            else {
                lContent = null;
                lTopLeftTree = null;
                lTopRightTree = null;
                lBottomLeftTree = null;
                lBottomRightTree = null;
            }

        }

        public boolean isEmpty() {
            return (lContent==null);
        }

        public void  setObject(Object pObject) {
            if (pObject!=null) {
                if (this.isEmpty()) {
                    lTopLeftTree = new QuadTree();
                    lTopRightTree = new QuadTree();
                    lBottomLeftTree = new QuadTree();
                    lBottomRightTree = new QuadTree();
                }
                lContent = pObject;
            }
        }

        public Object getObject() {
            return lContent  ;
        }

        public void setTopLeftTree(QuadTree pTree) {
            if (!this.isEmpty() && pTree!=null)
                lTopLeftTree = pTree;
        }

        public void setTopRightTree(QuadTree pTree) {
            if (! this.isEmpty() && pTree!=null)
                lTopRightTree = pTree;
        }

        public void setBottomLeftTree(QuadTree pTree) {
            if(!this.isEmpty() && pTree != null)
                lBottomLeftTree = pTree;
        }

        public void setlBottomRightTree(QuadTree pTree) {
            if(!this.isEmpty() && pTree != null)
                lBottomRightTree = pTree;
        }

        public QuadTree getTopLeftTree() {
            if (! this.isEmpty())
                return lTopLeftTree;
            else
                return null;
        }

        public QuadTree getTopRightTree() {
            if (! this.isEmpty())
                return lTopRightTree;
            else
                return null;
        }

        public QuadTree getBottomLeftTree() {
            if(!this.isEmpty())
                return lBottomLeftTree;
            else
                return null;
        }

        public QuadTree getBottomRightTree(){
            if(!this.isEmpty())
                return lBottomRightTree;
            else
                return null;
        }

        public void setEmpty() {
            lContent = null;
            lTopLeftTree = null;
            lTopRightTree = null;
            lBottomLeftTree = null;
            lBottomRightTree = null;
        }
    }
