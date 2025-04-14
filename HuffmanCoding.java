import java.util.ArrayList;
import java.util.PriorityQueue;

public class HuffmanCoding implements HuffmanInterface {
    private class NodeRL implements Comparable<NodeRL>{
        String freq;
        char value;
        NodeRL left;
        NodeRL right;

        public NodeRL(String freq, char value){
            this.freq = freq;
            this.value = value;
            left = right = null;
        }

        public NodeRL(NodeRL left, NodeRL right){
            this.left = left;
            this.right = right;
            this.freq = left.freq + right.freq;
            this.value = 0;
        }

        public int compareTo(NodeRL node){
            return Integer.parseInt(this.freq) - Integer.parseInt(node.freq);
        }
    }

    class HelperNode{
        String code;
        char value;
        HelperNode(String code, char value){
            this.code = code;
            this.value = value;
        }

        @Override
        public String toString() {
            return code;
        }
    }


    PriorityQueue<NodeRL> table = new PriorityQueue<>();
    NodeRL root;
    ArrayList<HelperNode> allIn = new ArrayList<>();
    ArrayList<String> codes = new ArrayList<>();
    ArrayList<Character> valueToCodes = new ArrayList<>();


    @Override
    public String decode(String codedMessage) {
        NodeRL node = root;
        String decodedString = "";
        if(codedMessage == null || codedMessage.length() == 0 || root == null){
            return "";
        }
        for(char c : codedMessage.toCharArray()){
            if(c == '0'){
                node = node.left;
            }
            if (c == '1'){
                node = node.right;
            }
            if(node.left == null || node.right == null){
                decodedString += node.value;
                node = root;
            }
        }
        return decodedString;
        
    }

    @Override
    public String encode(String message) {
    int[] frequncy = new int[256];

    for (char c : message.toCharArray()) {
        frequncy[c]++;
        
    }
    for(char c = 0; c < frequncy.length; c++){
        if(frequncy[c] > 0){
            NodeRL data = new NodeRL(String.valueOf(frequncy[c]), c);
            table.add(data);
        }
    }
    
    while (table.size() > 1) {
        NodeRL left = table.poll();
        NodeRL right = table.poll();
        NodeRL dataNode = new NodeRL(left, right);
        table.add(dataNode);
    }

    root = table.poll();

    makeCode(root, "");

    fusion(codes, valueToCodes);

    String enMessage = "";
    for(char c : message.toCharArray()){
        enMessage += findCode(c);
    }

        return enMessage;
    }


    String findCode(char c){
        for(HelperNode code : allIn){
            if(code.value == c){
                return code.code;
            }
        }
        return "";
    }

    void fusion(ArrayList<String> codes, ArrayList<Character> values){
        if(codes.size() < 1){
            return;
        }
        for(int i=0; i<codes.size(); i++){
            allIn.add(new HelperNode(codes.get(i), values.get(i)));
        }
    }
    
    void makeCode(NodeRL node, String code){
        if(node == null){ return;}
        if(node.left == null || node.right == null){
            codes.add(code);
            valueToCodes.add(node.value);
        }
        makeCode(node.left, code + "0");
        makeCode(node.right, code + "1");
    }
}


// 00 10 111 10 110 01
// R   a  f   a  i   l
//               R
//            L     A
//                I   F
// A has the highest frequency and should BE ON THE TOPPPPPPP.
// THE CODE WORKS STILL, I think I just did a binary search tree by accident, though