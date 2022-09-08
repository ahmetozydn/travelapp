package com.ahmetozaydin.logindemo.view

import android.R
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.ahmetozaydin.logindemo.databinding.ActivityNfcReaderBinding
import kotlinx.android.synthetic.main.activity_lines_to_map.*
import java.time.LocalDate
import java.time.Month
import java.util.concurrent.ThreadLocalRandom
import kotlin.experimental.and

class NfcReader : AppCompatActivity(),NfcAdapter.ReaderCallback{//press button xml
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var binding: ActivityNfcReaderBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNfcReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar: ActionBar? = supportActionBar//TODO
        actionBar?.setDisplayHomeAsUpEnabled(true)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter != null && nfcAdapter!!.isEnabled) {
            Toast.makeText(
                this, "NFC is enabled",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(this, "NO NFC Capabilities", Toast.LENGTH_SHORT).show()
        }
        binding.buttonShowTheCardInfo.setOnClickListener {
            if(binding.editTextCardId.text.trim().length == 14){//TODO
                val randomBalance = (0..100).random()
                binding.textViewBalance.text = "Balance : "+randomBalance+"\n"
           /*     val start = LocalDate.of(1989, Month.OCTOBER, 14)
                val end = LocalDate.now()
                val random = generateRandomDate(start,end)
                val comparisonResult = random!!.compareTo(end)//could crash
                while (true){
                    when(true) {
                        (comparisonResult > 0) -> {
                            binding.textViewDate.text = random.toString()
                            break
                        }
                        (comparisonResult < 0) -> {
                            generateRandomDate(start,end)
                        }
                        else -> {
                            binding.textViewDate.text = random.toString()
                            break
                        }
                    }
                }*/
            }
        }
    }
    public override fun onResume() {
        super.onResume()
        nfcAdapter?.enableReaderMode(
            this, this,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null
        )
    }

    public override fun onPause() {
        super.onPause()
        nfcAdapter?.disableReaderMode(this)
    }
    override fun onTagDiscovered(tag: Tag?) {
        /* val isoDep = IsoDep.get(tag)
         isoDep.connect()
         val response = isoDep.transceive(
             Utils.hexStringToByteArray(
                 "00A4040007A0000002471001"
             )
         )
         runOnUiThread {
             textView.append(
                 "\nCard Response: "
                         + Utils.toHex(response)
             )
         }
         isoDep.close()*/
/*
        if (intent.action.equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            val myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG) as Tag?
            val valueId = ByteArrayToHexString(myTag!!.id)
            textView.append(valueId)
            println("tag ID :$valueId")
        }
*/
        /* val nfcA = NfcA.get(tag)
         nfcA.connect()
         val aResponse = nfcA.transceive(Utils.hexStringToByteArray(
             "00A4040007A0000002471001"))
         runOnUiThread { textView.append("\nCard Response\n: "
                 + Utils.toHex(aResponse))
         }
         nfcA.close()
         val nfcB = NfcB.get(tag)
         nfcB.connect()
         val secondResponse = nfcB.transceive(Utils.hexStringToByteArray(
             "00A4040007A0000002471001"))
         runOnUiThread { textView.append("\nCard Response\n: "
                 + Utils.toHex(secondResponse))
         }
         nfcB.close()*/
        //println(NfcAdapter.EXTRA_ID)
        //val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        // get EXTRA_ID
        // val tagId = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)
        // get EXTRA_NDEF_MESSAGES
        runOnUiThread {
            if (tag != null) {
                val tagId = byteArrayToHexString(tag.id)
                binding.textView.text = "Card Id : $tagId"
                binding.editTextCardId.setText(tagId)
                Log.d(ContentValues.TAG, "CardId Hex:$tagId")//tag can be imported in a different way
                //println(tagId)

            }
        }
        val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        if (rawMsgs != null) {
            for (pa in rawMsgs) {
                val ndefMsg = pa as NdefMessage
                val records = ndefMsg.records
                for (rec in records) {
                    val uri: Uri = rec.toUri()
                    //binding.editTextCardId.text = uri.toString()
                    binding.textView.text = uri.toString()
                    break
                }
            }
        }

        Log.d(ContentValues.TAG, tag.toString())//tag can be imported in a different way
        runOnUiThread { Toast.makeText(this, "NFC Intent Received!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

/*
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
                // Process the messages array.
                println(NfcAdapter.EXTRA_ID)
                val firstRecord = messages[0]
                val msg = String(messages[0].records[0].payload)
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                Toast.makeText(
                    applicationContext, "Here is my text",
                    Toast.LENGTH_LONG
                ).show()
                textView.text = msg //my attempt to set my received data to "editText" field
            }
            val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            println(tag.toString())
            if(tag?.id !=null){
                val info = bytesToHex(tag.id)
                println(info)
            }

        }
*/

    }
    override fun onOptionsItemSelected(@NonNull item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected val hexArray =
        charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    private fun bytesToHex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        var v: Int
        for (j in bytes.indices) {
            v = (bytes[j] and 0xFF.toByte()).toInt()
            hexChars[j * 2] = hexArray[v ushr 4]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }

    private fun byteArrayToHexString(inarray: ByteArray): String {//id nin sonu 0 ile bitiyorsa görünmüyor.
        var i: Int
        var j: Int
        var `in`: Int
        val hex = arrayOf(
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "A",
            "B",
            "C",
            "D",
            "E",
            "F"
        )
        var out = ""
        j = 0
        while (j < inarray.size) {
            `in` = inarray[j].toInt() and 0xff
            i = `in` shr 4 and 0x0f
            out += hex[i]
            i = `in` and 0x0f
            out += hex[i]
            ++j
        }
        return out
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun generateRandomDate(startInclusive: LocalDate, endExclusive: LocalDate): LocalDate? {
        val startEpochDay: Long = startInclusive.toEpochDay()
        val endEpochDay: Long = endExclusive.toEpochDay()
        val randomDay: Long = ThreadLocalRandom
            .current()
            .nextLong(startEpochDay, endEpochDay)
        return LocalDate.ofEpochDay(randomDay)
    }

}