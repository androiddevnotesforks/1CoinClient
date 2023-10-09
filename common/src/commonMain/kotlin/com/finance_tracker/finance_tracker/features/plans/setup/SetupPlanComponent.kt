package com.finance_tracker.finance_tracker.features.plans.setup

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.finance_tracker.finance_tracker.MR
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.DialogCloseable
import com.finance_tracker.finance_tracker.core.common.decompose_ext.bottomDialogSlot
import com.finance_tracker.finance_tracker.core.common.injectViewModel
import com.finance_tracker.finance_tracker.core.ui.dialogs.DeleteDialogComponent
import dev.icerock.moko.resources.desc.desc
import kotlinx.serialization.Serializable
import org.koin.core.parameter.parametersOf

class SetupPlanComponent(
    params: SetupPlanScreenParams,
    componentContext: ComponentContext,
    private val close: () -> Unit
) : BaseComponent<SetupPlanViewModel>(componentContext) {

    override val viewModel: SetupPlanViewModel = injectViewModel { parametersOf(params) }

    private val bottomDialogNavigation: SlotNavigation<BottomDialogConfig> = SlotNavigation()
    val bottomDialogSlot: Value<ChildSlot<*, BottomDialogChild>> = bottomDialogSlot(
        source = bottomDialogNavigation,
        serializer = BottomDialogConfig.serializer()
    ) { config, childComponentContext ->
        when (config) {
            BottomDialogConfig.DeletePlanDialog -> {
                BottomDialogChild.DeletePlan(
                    component = DeleteDialogComponent(
                        componentContext = childComponentContext,
                        titleRes = MR.strings.deleting_plan.desc(),
                        onCancelClick = bottomDialogNavigation::dismiss,
                        onDeleteClick = bottomDialogNavigation::dismiss
                    )
                )
            }
        }
    }

    init {
        viewModel.handleComponentAction { action ->
            when (action) {
                Action.Close ->  {
                    close()
                }
                Action.DismissBottomDialog -> {
                    bottomDialogNavigation.dismiss()
                }
                Action.ShowDeletePlanDialog -> {
                    bottomDialogNavigation.activate(BottomDialogConfig.DeletePlanDialog)
                }
            }
        }
    }

    sealed interface BottomDialogChild : DialogCloseable {

        data class DeletePlan(
            val component: DeleteDialogComponent
        ) : BottomDialogChild
    }

    @Serializable
    sealed interface BottomDialogConfig {
        @Serializable
        data object DeletePlanDialog: BottomDialogConfig
    }

    sealed interface Action {
        data object Close: Action
        data object DismissBottomDialog: Action
        data object ShowDeletePlanDialog : Action
    }
}