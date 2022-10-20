import com.finance_tracker.finance_tracker.AppDatabase
import com.finance_tracker.finance_tracker.data.database.DatabaseInitializer
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val databaseModule = module {
    singleOf(::DatabaseInitializer)
    factory { get<AppDatabase>().transactionsEntityQueries }
    factory { get<AppDatabase>().smsMessageEntityQueries }
    factory { get<AppDatabase>().accountsEntityQueries }
    factory { get<AppDatabase>().categoriesEntityQueries }
    factory { get<AppDatabase>().accountColorsEntityQueries }
}