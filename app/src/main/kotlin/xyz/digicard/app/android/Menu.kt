package xyz.digicard.app.android

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.jayrave.falkon.dao.findAll
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import xyz.digicard.app.android.models.UsersTable

class Menu : AppCompatActivity() {

    override fun onResume() {
        super.onResume()

        // Doing the setup in `onResume` to refresh the page every time this is shown
        val userObjects = UsersTable.instance.dao.findAll()
        setContentView(R.layout.activity_menu)

        val recyclerView = findViewById<RecyclerView>(R.id.contact_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NewAdapter(userObjects)

        val sendNdef = findViewById<ImageView>(R.id.send_ndef)
        sendNdef.setOnClickListener { NfcSendActivity.start(this, CurrentUser.getId(this)!!) }
        sendNdef.setImageDrawable(
                IconicsDrawable(this, CommunityMaterial.Icon.cmd_nfc)
                        .actionBar()
                        .color(Color.WHITE)
        )

        val showQrCode = findViewById<ImageView>(R.id.show_qrcode)
        showQrCode.setOnClickListener {
            QrGeneratorActivity.startActivity(CurrentUser.getId(this)!!, this)
        }

        showQrCode.setImageDrawable(
                IconicsDrawable(this, CommunityMaterial.Icon.cmd_qrcode)
                        .actionBar()
                        .color(Color.WHITE)
        )

        val readQrCode = findViewById<ImageView>(R.id.read_qrcode)
        readQrCode.setOnClickListener {
            QrReaderActivity.startActivity(this)
        }

        readQrCode.setImageDrawable(
                IconicsDrawable(this, CommunityMaterial.Icon.cmd_qrcode_scan)
                        .actionBar()
                        .color(Color.WHITE)
        )

        val nearbyConnection = findViewById<ImageView>(R.id.nearby_connections)
        nearbyConnection.setImageDrawable(
                IconicsDrawable(this, CommunityMaterial.Icon.cmd_access_point)
                        .actionBar()
                        .color(Color.WHITE)
        )
        nearbyConnection.setOnClickListener {
            startActivity(Intent(this, NearbyConnections::class.java))
        }
    }
}
