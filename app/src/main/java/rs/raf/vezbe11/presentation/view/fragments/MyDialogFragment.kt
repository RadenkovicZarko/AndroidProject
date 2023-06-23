package rs.raf.vezbe11.presentation.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MyDialogFragment : DialogFragment() {
    companion object {
        private const val ARG_TEXT = "text"

        fun newInstance(text: String): MyDialogFragment {
            val fragment = MyDialogFragment()
            val args = Bundle()
            args.putString(ARG_TEXT, text)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Retrieve the text from the arguments
        val text = arguments?.getString(ARG_TEXT)

        val builder = AlertDialog.Builder(requireContext())
            .setTitle("Category details")
            .setMessage(text)
            .setPositiveButton("OK") { dialog, _ ->
                // Handle positive button click if needed
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                // Handle negative button click if needed
                dialog.dismiss()
            }
        return builder.create()
    }
}