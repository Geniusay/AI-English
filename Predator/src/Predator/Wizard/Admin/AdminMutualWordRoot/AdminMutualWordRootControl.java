package Predator.Wizard.Admin.AdminMutualWordRoot;

import Pinecone.Framework.Util.JSON.JSONObject;
import Pinecone.Framework.Util.Net.Illumination.HostMatrix;
import Pinecone.Framework.Util.Net.Illumination.prototype.JSONBasedControl;
import Predator.System.Predator;
import Predator.Util.PredatorControlHelper;
import Predator.Util.PredatorHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TreeMap;

public class AdminMutualWordRootControl extends AdminMutualWordRoot implements JSONBasedControl {
    public AdminMutualWordRootControl() {
    }

    public AdminMutualWordRootControl(HostMatrix parentSystem ){
        super(parentSystem);
    }

    @Override
    public void defaultGenie() throws Exception {
        super.defaultGenie();
    }

    public void appendNewWord() throws IOException,SQLException {
        System.out.println("hahaha");
        JSONObject $_SPOST = this.$_POST( true );
        JSONObject en2ch_data = $_SPOST.yokedSharedFromKey( new String[]{ "en_word", "cn_simple_mean" } );

        String szNewWord = $_SPOST.optString("en_word");
        if( this.mysql().countFromTable(
                String.format(
                        "SELECT COUNT(*) FROM %s WHERE `en_word` = '%s'" ,
                        this.tableName(Predator.TABLE_MUTUAL_WORD_ROOTS), szNewWord )
        ) > 0 ){
            this.alert( "'"  + szNewWord +"' has been existed.", 0, -1 );
        }

        boolean bRes = this.mysql().insertWithArray( Predator.TABLE_EN2CH_DICTIONARY, en2ch_data.getMap() ) > 0;
        $_SPOST.remove( "cn_simple_mean" );
        bRes &= this.mysql().insertWithArray( Predator.TABLE_MUTUAL_WORD_ROOTS, $_SPOST.getMap() ) > 0;

        this.checkResult( bRes, null, this.spawnActionQuerySpell() );
    }

    public void deleteOneWord() throws IOException,SQLException {
        this.checkResult(
                this.deleteOneWord( null )
        );
    }

    public boolean deleteOneWord( String szID ) throws IOException, SQLException {
        JSONObject $_SGET = this.$_GET(true);
        szID = PredatorHelper.assertString( szID ) ? szID : $_SGET.optString( "id" );
        if( !PredatorHelper.assertString( szID ) ){
            return false;
        }

        TreeMap<String, String > map = new TreeMap<>();
        map.put( "id", szID );
        /* MySQL Tagger is on **/
        return this.mysql().deleteWithArray( Predator.TABLE_MUTUAL_WORD_ROOTS, map ) > 0;
    }

    public void modifyOneWord() throws IOException, SQLException {
        JSONObject $_SPOST = this.$_POST( true );

        JSONObject condition  = $_SPOST.yokedSharedFromKey( "en_word" );
        String szOldWord = $_SPOST.optString("en_word"), szNewWord = $_SPOST.optString("en_word_new");
        if( !szOldWord.equals(szNewWord) ){
            $_SPOST.put( "en_word", szNewWord );
        }
        JSONObject en2ch_data = $_SPOST.yokedSharedFromKey( new String[]{ "en_word", "cn_simple_mean" } );
        $_SPOST.remove("en_word_new");

        boolean bRes = this.mysql().updateWithArray( Predator.TABLE_EN2CH_DICTIONARY, en2ch_data.getMap(), condition.getMap() ) > 0;
        $_SPOST.remove( "cn_simple_mean" );
        bRes &= this.mysql().updateWithArray( Predator.TABLE_MUTUAL_WORD_ROOTS, $_SPOST.getMap(), condition.getMap() ) > 0;

        this.checkResult( bRes );
    }

    public void wordMassDelete() throws IOException, SQLException {
        PredatorControlHelper.commonMassDelete( this, "id[]", "deleteOneWord" );
    }

}
